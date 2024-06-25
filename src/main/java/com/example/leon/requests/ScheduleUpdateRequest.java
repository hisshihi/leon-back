package com.example.leon.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleUpdateRequest {

    private List<LocalDate> dates;
    private List<LocalTime> times;
//    private boolean isWorking;

}
