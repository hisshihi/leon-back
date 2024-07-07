package com.example.leon.controllers;

import com.example.leon.domain.entities.AdditionalServiceAppointment;
import com.example.leon.services.AdditionalServiceAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("additional-service")
@RequiredArgsConstructor
public class AdditionalServiceAppointmentController {

    private final AdditionalServiceAppointmentService additionalServiceAppointmentService;

    @PostMapping
    private ResponseEntity<Void> create(@RequestBody AdditionalServiceAppointment serviceAppointment) {
        additionalServiceAppointmentService.create(serviceAppointment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<List<AdditionalServiceAppointment>> findAll() {
        List<AdditionalServiceAppointment> appointment = additionalServiceAppointmentService.findAll();
        return ResponseEntity.ok(new ArrayList<>(appointment));
    }

}
