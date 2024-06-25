package com.example.leon.services.impl;

import com.example.leon.domain.entities.Masters;
import com.example.leon.domain.entities.Schedule;
import com.example.leon.domain.entities.TimeSlot;
import com.example.leon.repositories.ScheduleRepository;
import com.example.leon.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;


    @Override
    public void createMonthlySchedule(Long masterId, int year, int month, List<LocalDate> nonWorkingDays, List<LocalTime> workingHours) {
//        Инициализация списка расписаний
        // Создаётся пустой список, который будет содержать расписания на каждый день месяца
        List<Schedule> schedules = new ArrayList<>();
        /*
         * Определение начальной и конечной даты:
         * Определяются начальная и конечная даты для заданного месяца. startDate — это первый день месяца, а endDate — последний день месяца.
         * */
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        /*
         * Цикл по дням месяца:
         * Этот цикл проходит по всем дням месяца от startDate до endDate.
         * */
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            /*
             * Создание объекта Schedule для каждого дня:
             * Для каждого дня создаётся объект Schedule, который связывается с мастером (masterId) и конкретной датой (date).
             * */
            Schedule schedule = Schedule.builder()
                    .master(new Masters(masterId, null, null, null, null, null, null))
                    .date(date)
                    .build();

            /*
             * Инициализация списка временных интервалов (TimeSlot):
             * Внутри цикла по дням есть ещё один цикл, который проходит по всем рабочим часам (workingHours).
             * Для каждого рабочего часа создаётся объект TimeSlot, который связывается с текущим расписанием (schedule) и временем (time).
             * Флаг isWorking устанавливается в зависимости от того, является ли текущая дата выходным днём (nonWorkingDays).
             * */
            List<TimeSlot> timeSlots = new ArrayList<>();
            for (LocalTime time : workingHours) {
                boolean isWorking = !nonWorkingDays.contains(date);
                TimeSlot timeSlot = TimeSlot.builder()
                        .schedule(schedule)
                        .time(time)
                        .isWorking(isWorking)
                        .build();
                timeSlots.add(timeSlot);
            }
            /*
             * Добавление временных интервалов в расписание:
             * После создания всех временных интервалов для текущего дня, они добавляются в объект Schedule.
             * */
            schedule.setTimeSlots(timeSlots);
            /*
             * Добавление расписания в список:
             * Объект Schedule добавляется в общий список расписаний.
             * */
            schedules.add(schedule);
        }

        scheduleRepository.saveAll(schedules);
    }

    @Override
    public List<Schedule> getScheduleForMaster(Long masterId) {
        return scheduleRepository.findByMasterId(masterId);
    }

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
}
