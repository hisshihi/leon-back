package com.example.leon.domain.entities;

import com.example.leon.domain.dto.ScheduleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DaySchedule {

    private LocalDate date;
    private List<ScheduleDto> schedules;

}
