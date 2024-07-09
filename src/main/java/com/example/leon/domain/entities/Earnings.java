package com.example.leon.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Earnings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_earnings", nullable = false)
    private int totalEarnings;

    @Column(nullable = false)
    private Long masterId;

//    todo: добавить сколько заработал каждый мастер

}
