package com.example.leon.services.impl;

import com.example.leon.domain.dto.ScheduleDto;
import com.example.leon.domain.entities.*;
import com.example.leon.mappers.impl.TimeSlotMapper;
import com.example.leon.repositories.AppointmentRepository;
import com.example.leon.repositories.ScheduleRepository;
import com.example.leon.services.ScheduleService;
import com.example.leon.services.ServiceAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AppointmentRepository appointmentRepository;
    private final ServiceAppointmentService serviceAppointmentService;
    private final TimeSlotMapper timeSlotMapper;

    /**
     * Создает расписание на месяц для указанного мастера.
     *
     * @param masterId       ID мастера
     * @param year           Год
     * @param month          Месяц
     * @param nonWorkingDays Список дней, когда мастер не работает
     * @param workingHours   Список рабочих часов
     */
    @Override
    public void createMonthlySchedule(Long masterId, int year, int month, List<LocalDate> nonWorkingDays, List<LocalTime> workingHours) {
        List<Schedule> schedules = new ArrayList<>();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        // Проходим по каждому дню месяца
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            boolean isNonWorkingDay = nonWorkingDays.contains(date);
            Schedule schedule = Schedule.builder()
                    .master(new Masters(masterId, null, null, null, null, null, null, null, null, null, null))
                    .date(date)
                    .build();

            List<TimeSlot> timeSlots = new ArrayList<>();
            for (LocalTime time : workingHours) {
                TimeSlot timeSlot = TimeSlot.builder()
                        .schedule(schedule)
                        .time(time)
                        .isWorking(!isNonWorkingDay)
                        .build();
                timeSlots.add(timeSlot);
            }
            schedule.setTimeSlots(timeSlots);
            schedules.add(schedule);
        }

        // Сохраняем все расписания в базу данных
        scheduleRepository.saveAll(schedules);
    }

    /**
     * Получает все расписания для указанного мастера.
     *
     * @param masterId ID мастера
     * @return Список расписаний
     */
    @Override
    public List<Schedule> getScheduleForMaster(Long masterId) {
        return scheduleRepository.findByMasterId(masterId);
    }

    /**
     * Обновляет расписание для указанного мастера.
     *
     * @param masterId  ID мастера
     * @param dates     Список дат
     * @param times     Список временных слотов
     * @param isWorking Статус работы (работает/не работает)
     */
    @Override
    public void updateSchedule(Long masterId, List<LocalDate> dates, List<LocalTime> times, boolean isWorking) {
        for (LocalDate date : dates) {
            Schedule schedule = scheduleRepository.findByMasterIdAndDate(masterId, date);
            if (schedule != null) {
                // Список обновленных временных слотов
                List<TimeSlot> updatedTimeSlots = times.stream().map(time -> {
                    // Проверка на существующий слот с этим временем
                    TimeSlot existingSlot = schedule.getTimeSlots().stream()
                            .filter(slot -> slot.getTime().equals(time))
                            .findFirst()
                            .orElse(null);

                    if (existingSlot != null) {
                        existingSlot.setWorking(isWorking);
                        return existingSlot;
                    } else {
                        // Создание нового временного слота
                        return TimeSlot.builder()
                                .schedule(schedule)
                                .time(time)
                                .isWorking(isWorking)
                                .build();
                    }
                }).collect(Collectors.toList());

                // Очистка текущих временных слотов и добавление обновленных
                schedule.getTimeSlots().clear();
                schedule.getTimeSlots().addAll(updatedTimeSlots);
                scheduleRepository.save(schedule);
            }
        }
    }

    /**
     * Получает расписание на месяц, учитывая только те месяцы, для которых есть расписание.
     *
     * @param year  Год
     * @param month Месяц
     * @return Список расписаний на каждый день месяца
     */
    @Override
    public List<DaySchedule> getMonthlySchedule(int year, int month) {
        LocalDate latestScheduleDate = scheduleRepository.findLatestScheduleDate();
        if (latestScheduleDate == null) {
            return new ArrayList<>();
        }

        LocalDate requestedDate = LocalDate.of(year, month, 1);
        if (requestedDate.isAfter(latestScheduleDate)) {
            return new ArrayList<>();
        }

        LocalDate startDate = requestedDate.withDayOfMonth(1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Schedule> schedules = scheduleRepository.findByDateBetween(startDate, endDate);

        List<ScheduleDto> scheduleDtos = schedules.stream()
                .map(schedule -> new ScheduleDto(
                        schedule.getId(),
                        schedule.getDate(),
                        schedule.getTimeSlots().stream()
                                .map(timeSlotMapper::mapTo)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        Map<LocalDate, List<ScheduleDto>> groupedSchedules = scheduleDtos.stream()
                .collect(Collectors.groupingBy(ScheduleDto::getDate));

        List<DaySchedule> daySchedules = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            List<ScheduleDto> dailySchedules = groupedSchedules.getOrDefault(date, new ArrayList<>());
            daySchedules.add(new DaySchedule(date, dailySchedules));
        }

        return daySchedules;
    }


    /**
     * Получает расписание на указанный день.
     *
     * @param date Дата
     * @return Список расписаний на указанный день
     */
    @Override
    public List<Schedule> getDailySchedule(LocalDate date) {
        // Получаем все записи на указанную дату
        List<Appointment> appointments = appointmentRepository.findByDate(date);

        // Создаем мапу для быстрого доступа к ServiceAppointment по имени сервиса
        Map<String, ServiceAppointment> serviceAppointmentMap = serviceAppointmentService.findAll()
                .stream()
                .collect(Collectors.toMap(ServiceAppointment::getName, sa -> sa));

        // Собираем все занятые временные слоты
        Set<LocalTime> occupiedTimeSlots = new HashSet<>();
        for (Appointment appointment : appointments) {
            String serviceName = appointment.getService();
            ServiceAppointment serviceAppointment = serviceAppointmentMap.get(serviceName);

            // Если serviceAppointment найден, продолжаем
            if (serviceAppointment != null) {
                LocalTime appointmentTime = appointment.getTime();
                LocalTime executionTime = serviceAppointment.getExecutionTime();
                LocalTime endTime = appointmentTime.plusHours(executionTime.getHour()).plusMinutes(executionTime.getMinute());
                LocalTime startTime = appointmentTime.minusHours(executionTime.getHour()).minusMinutes(executionTime.getMinute());

                // Добавляем все слоты от startTime до endTime с шагом в 30 минут
                LocalTime currentTime = startTime;
                while (currentTime.isBefore(endTime)) {
                    occupiedTimeSlots.add(currentTime);
                    currentTime = currentTime.plusMinutes(30);
                }
            }
        }

        // Получаем расписание на указанную дату
        List<Schedule> schedules = scheduleRepository.findByDate(date);

        // Фильтруем временные слоты, исключая занятые
        for (Schedule schedule : schedules) {
            List<TimeSlot> availableTimeSlots = schedule.getTimeSlots().stream()
                    .filter(timeSlot -> !occupiedTimeSlots.contains(timeSlot.getTime()))
                    .collect(Collectors.toList());
            schedule.setTimeSlots(availableTimeSlots);
        }

        return schedules;
    }

    @Override
    public boolean scheduleExistsForMonth(Long masterId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        return scheduleRepository.existsByMasterIdAndDateBetween(masterId, startDate, endDate);
    }
}
