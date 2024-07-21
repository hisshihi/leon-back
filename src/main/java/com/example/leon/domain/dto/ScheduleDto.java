package com.example.leon.domain.dto;

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
public class ScheduleDto {

    private Long id;
    private LocalDate date;
    private List<TimeSlotDto> timeSlots;

}
