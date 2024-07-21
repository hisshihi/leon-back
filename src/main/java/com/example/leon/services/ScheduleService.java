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
    @CachePut(value = "schedule", key = "#masterId + '-' + #year + '-' + #month")
    void createMonthlySchedule(Long masterId, int year, int month, List<LocalDate> nonWorkingDays, List<LocalTime> workingHours);

    List<Schedule> getScheduleForMaster(Long masterId);

    @CacheEvict(value = "schedule", allEntries = true)
    @CachePut(value = "schedule", key = "#masterId + '-' + #dates.hashCode() + '-' + #times.hashCode()")
    void updateSchedule(Long masterId, List<LocalDate> dates, List<LocalTime> times, boolean isWorking);

    @Cacheable(value = "schedule", key = "#year + '-' + #month")
    List<DaySchedule> getMonthlySchedule(int year, int month);

    @Cacheable(value = "schedule", key = "#date")
    List<Schedule> getDailySchedule(LocalDate date);

    @Cacheable(value = "schedule", key = "#masterId + '-' + #year + '-' + #month")
    boolean scheduleExistsForMonth(Long masterId, int year, int month);
}
