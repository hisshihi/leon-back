package com.example.leon.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    // Помогает убрать рекурсию из json
    @JsonBackReference
    private Schedule schedule;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "is_working", nullable = false)
    private boolean isWorking;

}
