package com.example.leon.controllers;

import com.example.leon.domain.entities.ServiceAppointment;
import com.example.leon.services.ServiceAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("service")
@RequiredArgsConstructor
public class ServiceAppointmentController {

    private final ServiceAppointmentService serviceAppointmentService;

    @PostMapping
    private ResponseEntity<Void> create(@RequestBody ServiceAppointment serviceAppointment) {
        serviceAppointmentService.createService(serviceAppointment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<List<ServiceAppointment>> findAll() {
        List<ServiceAppointment> serviceAppointmentList = serviceAppointmentService.findAll();
        return ResponseEntity.ok(new ArrayList<>(serviceAppointmentList));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceAppointmentService.deleteService(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
