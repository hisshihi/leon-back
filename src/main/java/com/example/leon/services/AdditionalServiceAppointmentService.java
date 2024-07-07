package com.example.leon.services;

import com.example.leon.domain.entities.AdditionalServiceAppointment;

import java.util.List;

public interface AdditionalServiceAppointmentService {
    void create(AdditionalServiceAppointment serviceAppointment);

    List<AdditionalServiceAppointment> findAll();
}
