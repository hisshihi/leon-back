package com.example.leon.services;

import com.example.leon.domain.entities.DaySchedule;
import com.example.leon.domain.entities.Schedule;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleService {

    @CacheEvict(value = "schedule", allEntries = true)
    @CachePut(value = "schedule", key = "#masterId")
    void createMonthlySchedule(Long masterId, int year, int month, List<LocalDate> nonWorkingDays, List<LocalTime> workingHours);

    List<Schedule> getScheduleForMaster(Long masterId);

    @CacheEvict(value = "schedule", allEntries = true)
    void updateSchedule(Long masterId, List<LocalDate> dates, List<LocalTime> times, boolean isWorking);

    @Cacheable("schedule")
    List<DaySchedule> getMonthlySchedule(int year, int month);

    @Cacheable("schedule")
    List<Schedule> getDailySchedule(LocalDate date);

    @Cacheable("schedule")
    boolean scheduleExistsForMonth(Long masterId, int year, int month);
}
