package com.example.leon.controllers;

import com.example.leon.domain.entities.DaySchedule;
import com.example.leon.domain.entities.Schedule;
import com.example.leon.requests.ScheduleUpdateRequest;
import com.example.leon.services.ScheduleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "schedule")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestParam Long masterId,
            @RequestParam int year,
            @RequestParam int month,
            @RequestBody(required = false) List<LocalDate> nonWorkingDays,
            @RequestParam List<LocalTime> workingHours) {

        if (nonWorkingDays == null) {
            nonWorkingDays = Collections.emptyList(); // Если список не передан, используем пустой список
        }

        if (scheduleService.scheduleExistsForMonth(masterId, year, month)) {
            log.info("Расписание на " + year + "-" + month + " уже есть");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

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

    @Transactional
    @GetMapping(path = "/month")
    public ResponseEntity<List<DaySchedule>> getSchedule(
            @RequestParam int year,
            @RequestParam int month
    ) {
        log.info("Поиск расписания на месяц = {}", month);
        List<DaySchedule> schedules = scheduleService.getMonthlySchedule(year, month);
        log.info("Поиск выполнен успешно");
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
