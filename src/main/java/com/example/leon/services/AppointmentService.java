package com.example.leon.services;

import com.example.leon.domain.entities.Appointment;
import org.springframework.cache.annotation.CacheEvict;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {

    @CacheEvict(value = "schedule", allEntries = true)
    void create(Appointment appointment);

    List<Appointment> findAll();

    boolean existsByDateAndTime(LocalDate date, LocalTime time);

    List<Appointment> findAllByMaster(Principal principal);

    @CacheEvict(value = "schedule", allEntries = true)
    void updatedAppointment(Long id, Appointment updatedAppointment);

    void delete(Long id);

    List<Appointment> findByClientName(String name);

    int getTotalEarnings();

    int getEarningsByMaster(Long masterId);

    List<Appointment> findByDate(LocalDate date);
}
