package com.example.leon.controllers;

import com.example.leon.domain.entities.Appointment;
import com.example.leon.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    private ResponseEntity<Void> createAppointment(@RequestBody Appointment appointment) {
        appointmentService.create(appointment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
