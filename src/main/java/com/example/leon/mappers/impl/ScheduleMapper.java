package com.example.leon.mappers.impl;

import com.example.leon.domain.dto.ScheduleDto;
import com.example.leon.domain.entities.Schedule;
import com.example.leon.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleMapper implements Mapper<Schedule, ScheduleDto> {

    private final ModelMapper modelMapper;

    @Override
    public ScheduleDto mapTo(Schedule schedule) {
        return modelMapper.map(schedule, ScheduleDto.class);
    }

    @Override
    public Schedule mapFrom(ScheduleDto scheduleDto) {
        return modelMapper.map(scheduleDto, Schedule.class);
    }
}
