package com.example.leon.services;

import com.example.leon.domain.entities.DaySchedule;
import com.example.leon.domain.entities.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleService {

    void createMonthlySchedule(Long masterId, int year, int month, List<LocalDate> nonWorkingDays, List<LocalTime> workingHours);

    List<Schedule> getScheduleForMaster(Long masterId);

    void updateSchedule(Long masterId, List<LocalDate> dates, List<LocalTime> times, boolean isWorking);

    List<DaySchedule> getMonthlySchedule(int year, int month);

    List<Schedule> getDailySchedule(LocalDate date);
}
