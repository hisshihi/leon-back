package com.example.leon.services.impl;

import com.example.leon.domain.entities.Appointment;
import com.example.leon.domain.entities.Earnings;
import com.example.leon.domain.entities.Masters;
import com.example.leon.repositories.AppointmentRepository;
import com.example.leon.repositories.EarningRepository;
import com.example.leon.services.AppointmentService;
import com.example.leon.services.MastersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final MastersService mastersService;
    private final EarningRepository earningRepository;
    private final TelegramServiceImpl telegramService;

    @Override
    public void create(Appointment appointment) {
        appointment.setDateCreated(LocalDate.now());

        if (appointment.getClientPhone().startsWith("+8")) {
            String updateClientPhone = appointment.getClientPhone().replaceFirst("\\+8", "+7");
            appointment.setClientPhone(updateClientPhone);
        }

        appointmentRepository.save(appointment);

        Optional<Masters> master = mastersService.findById(appointment.getMaster().getId());

        String message = String.format("Новая запись.\nИмя клиента: %s\nДата: %s\nВремя: %s\nИмя мастера: %s %s\nУслуга: %s",
                appointment.getClientName(),
                appointment.getDate(),
                appointment.getTime(),
                master.get().getFirstName(),
                master.get().getLastName(),
                appointment.getService());

//        telegramService.sendMessage(message);
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
        existingEarnings.setMasterEarnings(existingEarnings.getMasterEarnings() + (earnings * 40) / 100);
        earningRepository.save(existingEarnings);
    }

    @Override
    public int getTotalEarnings() {
        return earningRepository.findTotalEarnings();
    }

    @Override
    public int getEarningsByMaster(Long masterId) {
        return earningRepository.findByMasterId(masterId)
                .map(Earnings::getMasterEarnings)
                .orElse(0);
    }

    @Override
    public List<Appointment> findByDate(LocalDate date) {
        return appointmentRepository.findByDate(date);
    }

}
