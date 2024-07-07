package com.example.leon.repositories;

import com.example.leon.domain.entities.AdditionalServiceAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalServiceAppointmentRepository extends JpaRepository<AdditionalServiceAppointment, Long> {
}
