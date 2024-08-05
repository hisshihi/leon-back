package com.example.leon.services;

import com.example.leon.domain.entities.ServiceAppointment;

import java.util.List;

public interface ServiceAppointmentService {
    void createService(ServiceAppointment serviceAppointment);

    List<ServiceAppointment> findAll();

    void deleteService(Long id);
}
