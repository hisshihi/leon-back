package com.example.leon.mappers.impl;

import com.example.leon.domain.dto.TimeSlotDto;
import com.example.leon.domain.entities.TimeSlot;
import com.example.leon.mappers.Mapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TimeSlotMapper implements Mapper<TimeSlot, TimeSlotDto> {

    private final ModelMapper modelMapper;

    @Override
    public TimeSlotDto mapTo(TimeSlot timeSlot) {
        return modelMapper.map(timeSlot, TimeSlotDto.class);
    }

    @Override
    public TimeSlot mapFrom(TimeSlotDto timeSlotDto) {
        return modelMapper.map(timeSlotDto, TimeSlot.class);
    }
}
