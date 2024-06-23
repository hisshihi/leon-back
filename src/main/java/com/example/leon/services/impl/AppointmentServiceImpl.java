package com.example.leon.services.impl;

import com.example.leon.domain.entities.Appointment;
import com.example.leon.repositories.AppointmentRepository;
import com.example.leon.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public void create(Appointment appointment) {
        appointmentRepository.save(appointment);
    }
}
