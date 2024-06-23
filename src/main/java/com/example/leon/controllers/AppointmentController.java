package com.example.leon.controllers;

import com.example.leon.domain.entities.Appointment;
import com.example.leon.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    private ResponseEntity<Void> createAppointment(@RequestBody Appointment appointment) {
        if (!appointmentService.existsByDateAndTime(appointment.getDate(), appointment.getTime())) {
            appointmentService.create(appointment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    private ResponseEntity<List<Appointment>> getListAppointments() {
        List<Appointment> appointments = appointmentService.findAll();
        return new ResponseEntity<>(new ArrayList<>(appointments), HttpStatus.OK);
    }

}
