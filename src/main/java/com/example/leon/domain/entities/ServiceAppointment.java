package com.example.leon.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ServiceAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_service")
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "serviceAppointment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ServicePrice> prices = new ArrayList<>();

    private LocalTime executionTime;

}

