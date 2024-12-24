package com.example.leon.repositories;

import com.example.leon.domain.entities.Appointment;
import com.example.leon.domain.entities.Masters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, PagingAndSortingRepository<Appointment, Long> {
    boolean existsByDateAndTime(LocalDate date, LocalTime time);

    List<Appointment> findAllByMaster(Masters masters);

    @Query("SELECT a FROM Appointment a WHERE MONTH(a.date) = :month AND YEAR(a.date) = :year")
    Page<Appointment> findAllOrderByDateCreatedDesc(@Param("month") int month, @Param("year") int year, Pageable pageable);

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.master = :master " +
            "AND MONTH(a.date) = :month " +
            "AND YEAR(a.date) = :year " +
            "ORDER BY a.dateCreated DESC")
    Page<Appointment> findAllByMasterOrderByDateCreatedDesc(@Param("master") Masters master,
                                             @Param("month") int month,
                                             @Param("year") int year,
                                             Pageable pageable);


    List<Appointment> findByClientNameContainingIgnoreCase(String clientName);

    List<Appointment> findByDate(LocalDate date);

    boolean existsByMasterId(Long id);
}
