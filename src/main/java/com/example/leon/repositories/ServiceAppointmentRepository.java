package com.example.leon.repositories;

import com.example.leon.domain.entities.ServiceAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceAppointmentRepository extends JpaRepository<ServiceAppointment, Long> {
}
