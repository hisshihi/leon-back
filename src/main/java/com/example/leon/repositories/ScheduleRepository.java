package com.example.leon.repositories;

import com.example.leon.domain.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByMasterId(Long masterId);

    Schedule findByMasterIdAndDate(Long masterId, LocalDate date);

    List<Schedule> findByMasterIdAndDateBetween(Long masterId, LocalDate startDate, LocalDate endDate);

    List<Schedule> findByDate(LocalDate date);

    List<Schedule> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT MAX(s.date) FROM Schedule s")
    LocalDate findLatestScheduleDate();

    // Для загрузки мастера сразу с расписанием
    @Query("SELECT s FROM Schedule s JOIN FETCH s.master WHERE s.date BETWEEN :startDate AND :endDate")
    List<Schedule> findByDateBetweenWithMaster(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM Schedule s JOIN FETCH s.master WHERE s.date = :date")
    List<Schedule> findByDateWithMaster(@Param("date") LocalDate date);

    boolean existsByMasterIdAndDateBetween(Long masterId, LocalDate startDate, LocalDate endDate);
}
