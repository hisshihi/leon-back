package com.example.leon.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlotDto {
    Long id;
    LocalTime startTime;
    LocalTime endTime;
    boolean isWorking;
}