package com.example.leon.services.impl;

import com.example.leon.domain.entities.DaySchedule;
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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;


    @Override
    public void createMonthlySchedule(Long masterId, int year, int month, List<LocalDate> nonWorkingDays, List<LocalTime> workingHours) {
        List<Schedule> schedules = new ArrayList<>();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            boolean isNonWorkingDay = nonWorkingDays.contains(date);
            Schedule schedule = Schedule.builder()
                    .master(new Masters(masterId, null, null, null, null, null, null))
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
        Map<LocalDate, List<Schedule>> groupedSchedules = schedules.stream()
                .collect(Collectors.groupingBy(Schedule::getDate));

        List<DaySchedule> daySchedules = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            List<Schedule> dailySchedules = groupedSchedules.getOrDefault(date, new ArrayList<>());
            daySchedules.add(new DaySchedule(date, dailySchedules));
        }

        return daySchedules;
    }

    @Override
    public List<Schedule> getDailySchedule(LocalDate date) {
        return scheduleRepository.findByDate(date);
    }
}
