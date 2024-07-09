package com.example.leon.services.impl;

import com.example.leon.domain.entities.Appointment;
import com.example.leon.domain.entities.Earnings;
import com.example.leon.domain.entities.Masters;
import com.example.leon.repositories.AppointmentRepository;
import com.example.leon.repositories.EarningRepository;
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
    private final EarningRepository earningRepository;

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

        updateEarnings(appointment.getMaster().getId(), appointment.getAllPrice());

        appointmentRepository.save(appointment);
    }

    @Override
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> findByClientName(String name) {
        return appointmentRepository.findByClientNameContainingIgnoreCase(name);
    }

//    Обновление earnings
    private void updateEarnings(Long masterId, int earnings) {
        Earnings existingEarnings = earningRepository.findByMasterId(masterId).orElseGet(() -> Earnings.builder().masterId(masterId).totalEarnings(0).build());
        existingEarnings.setTotalEarnings(existingEarnings.getTotalEarnings() + earnings);
        earningRepository.save(existingEarnings);
    }

    @Override
    public int getTotalEarnings() {
        return earningRepository.findTotalEarnings();
    }

    @Override
    public int getEarningsByMaster(Long masterId) {
        return earningRepository.findByMasterId(masterId)
                .map(Earnings::getTotalEarnings)
                .orElse(0);
    }

}
