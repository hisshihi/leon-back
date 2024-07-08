package com.example.leon.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "client_name", nullable = false)
    private String clientName;
    @Column(name = "service", nullable = false)
    private String service;
    @Column(name = "client_phone", nullable = false)
    private String clientPhone;

//    У каждого мастера может быть много записей но у записи может быть только один мастер
    @JoinColumn(name = "master", nullable = false)
    @ManyToOne
    private Masters master;

    @Column(name = "all_price", nullable = true)
    private int allPrice;

    @Column(nullable = true)
    private int priceForMainService;

    @Column(nullable = true)
    private int priceForAdditionalService;

    // Связь с AdditionalServiceAppointment
    @ManyToMany
    @JoinTable(
            name = "appointment_additional_service",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "additional_service_id")
    )
    private List<AdditionalServiceAppointment> additionalServices = new ArrayList<>();

}
