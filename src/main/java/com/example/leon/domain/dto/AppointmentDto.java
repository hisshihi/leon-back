package com.example.leon.domain.dto;

import com.example.leon.domain.entities.Masters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentDto {

    private Long id;
    private LocalDate date;
    private LocalTime time;
    private String clientName;
    private String service;
    private MasterDtoSmall masterDtoSmall;


}
