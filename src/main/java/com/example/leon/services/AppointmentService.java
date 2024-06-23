package com.example.leon.services;

import com.example.leon.domain.entities.Appointment;

import java.util.List;

public interface AppointmentService {
    void create(Appointment appointment);

    List<Appointment> findAll();
}
