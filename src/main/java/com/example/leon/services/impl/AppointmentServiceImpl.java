package com.example.leon.services.impl;

import com.example.leon.domain.entities.Appointment;
import com.example.leon.repositories.AppointmentRepository;
import com.example.leon.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public void create(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return appointmentRepository.existsByDateAndTime(date, time);
    }

}
