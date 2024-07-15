package com.example.leon.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ServicePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "length_in_sm")
    private Integer length;

    @Column(name = "price")
    private Integer price;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "service_appointment_id")
    @JsonBackReference
    private ServiceAppointment serviceAppointment;
}
