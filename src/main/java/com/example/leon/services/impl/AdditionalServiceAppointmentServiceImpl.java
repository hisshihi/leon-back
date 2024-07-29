package com.example.leon.services.impl;

import com.example.leon.domain.entities.AdditionalServiceAppointment;
import com.example.leon.repositories.AdditionalServiceAppointmentRepository;
import com.example.leon.services.AdditionalServiceAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdditionalServiceAppointmentServiceImpl implements AdditionalServiceAppointmentService {

    private final AdditionalServiceAppointmentRepository additionalServiceAppointmentRepository;

    @Override
    public void create(AdditionalServiceAppointment serviceAppointment) {
        additionalServiceAppointmentRepository.save(serviceAppointment);
    }

    @Override
    public List<AdditionalServiceAppointment> findAll() {
        return additionalServiceAppointmentRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        additionalServiceAppointmentRepository.deleteById(id);
    }
}
