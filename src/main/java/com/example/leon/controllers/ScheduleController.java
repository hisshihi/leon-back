package com.example.leon.controllers;

import com.example.leon.domain.entities.DaySchedule;
import com.example.leon.domain.entities.Schedule;
import com.example.leon.requests.ScheduleUpdateRequest;
import com.example.leon.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestParam Long masterId,
            @RequestParam int year,
            @RequestParam int month,
            @RequestBody List<LocalDate> nonWorkingDays,
            @RequestParam List<LocalTime> workingHours) {
        scheduleService.createMonthlySchedule(masterId, year, month, nonWorkingDays, workingHours);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Получение всех расписаний
    @GetMapping("/day")
    public ResponseEntity<List<Schedule>> getScheduleForDay(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam int day) {
        LocalDate date = LocalDate.of(year, month, day);
        List<Schedule> schedules = scheduleService.getDailySchedule(date);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping(path = "/month")
    public ResponseEntity<List<DaySchedule>> getSchedule(
            @RequestParam int year,
            @RequestParam int month
    ) {
        List<DaySchedule> schedules = scheduleService.getMonthlySchedule(year, month);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    // Обновиление данных
    @PutMapping("/update")
    public ResponseEntity<Void> updateTimeSlotsForDay(
            @RequestParam Long masterId,
            @RequestParam boolean isWorking,
            @RequestBody ScheduleUpdateRequest updateRequest
    ) {
        scheduleService.updateSchedule(masterId, updateRequest.getDates(), updateRequest.getTimes(), isWorking);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
