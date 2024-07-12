package com.example.leon.mappers.impl;

import com.example.leon.domain.dto.ServiceAppointmentDto;
import com.example.leon.domain.entities.ServiceAppointment;
import com.example.leon.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceAppointmentMapper implements Mapper<ServiceAppointment, ServiceAppointmentDto> {

    private final ModelMapper modelMapper;

    @Override
    public ServiceAppointmentDto mapTo(ServiceAppointment serviceAppointment) {
        return modelMapper.map(serviceAppointment, ServiceAppointmentDto.class);
    }

    @Override
    public ServiceAppointment mapFrom(ServiceAppointmentDto serviceAppointmentDto) {
        return modelMapper.map(serviceAppointmentDto, ServiceAppointment.class);
    }
}
