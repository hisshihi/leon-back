package com.example.leon.services.impl;

import com.example.leon.domain.entities.ServiceAppointment;
import com.example.leon.domain.entities.ServicePrice;
import com.example.leon.repositories.ServiceAppointmentRepository;
import com.example.leon.services.ServiceAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceAppointmentServiceImpl implements ServiceAppointmentService {

    private final ServiceAppointmentRepository serviceAppointmentRepository;

    @Override
    public void createService(ServiceAppointment serviceAppointment) {
        for (ServicePrice price : serviceAppointment.getPrices()) {
            price.setServiceAppointment(serviceAppointment);
        }
        serviceAppointmentRepository.save(serviceAppointment);
    }

    @Override
    public List<ServiceAppointment> findAll() {
        return serviceAppointmentRepository.findAll();
    }
}
