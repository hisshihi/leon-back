package com.example.leon.repositories;

import com.example.leon.domain.entities.Appointment;
import com.example.leon.domain.entities.Masters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDateAndTime(LocalDate date, LocalTime time);

    List<Appointment> findAllByMaster(Masters masters);

    @Query("SELECT a FROM Appointment a ORDER BY a.dateCreated DESC")
    List<Appointment> findAllOrderByDateCreatedDesc();

    @Query("SELECT a FROM Appointment a WHERE a.master = :master ORDER BY a.dateCreated DESC")
    List<Appointment> findAllByMasterOrderByDateCreatedDesc(@Param("master") Masters masters);

    List<Appointment> findByClientNameContainingIgnoreCase(String clientName);

}
