package com.example.leon.services.impl;

import com.example.leon.domain.entities.Appointment;
import com.example.leon.domain.entities.Masters;
import com.example.leon.repositories.AppointmentRepository;
import com.example.leon.services.AppointmentService;
import com.example.leon.services.MastersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final MastersService mastersService;

    @Override
    public void create(Appointment appointment) {
        appointment.setDateCreated(LocalDate.now());
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAllOrderByDateCreatedDesc();
    }

    @Override
    public boolean existsByDateAndTime(LocalDate date, LocalTime time) {
        return appointmentRepository.existsByDateAndTime(date, time);
    }

    @Override
    public List<Appointment> findAllByMaster(Principal principal) {
        Masters masters = (Masters) mastersService.masterDetailService().loadUserByUsername(principal.getName());
        return appointmentRepository.findAllByMasterOrderByDateCreatedDesc(masters);
    }

    @Override
    public void updatedAppointment(Long id, Appointment updatedAppointment) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Запись не существует"));

        appointment.setDate(updatedAppointment.getDate());
        appointment.setTime(updatedAppointment.getTime());
        appointment.setClientName(updatedAppointment.getClientName());
        appointment.setService(updatedAppointment.getService());
        appointment.setClientPhone(updatedAppointment.getClientPhone());
        appointment.setAllPrice(updatedAppointment.getAllPrice());
        appointment.setPriceForMainService(updatedAppointment.getPriceForMainService());
        appointment.setPriceForAdditionalService(updatedAppointment.getPriceForAdditionalService());
        appointment.setMaster(updatedAppointment.getMaster());
        appointment.setAdditionalServices(updatedAppointment.getAdditionalServices());

        appointmentRepository.save(appointment);
    }

}
