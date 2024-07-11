package com.example.leon.mappers.impl;

import com.example.leon.domain.dto.AppointmentDto;
import com.example.leon.domain.entities.Appointment;
import com.example.leon.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentMapper implements Mapper<Appointment, AppointmentDto> {

    private final ModelMapper modelMapper;

    @Override
    public AppointmentDto mapTo(Appointment appointment) {
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    @Override
    public Appointment mapFrom(AppointmentDto appointmentDto) {
        return modelMapper.map(appointmentDto, Appointment.class);
    }
}
