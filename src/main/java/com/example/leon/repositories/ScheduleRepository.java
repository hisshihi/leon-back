package com.example.leon.repositories;

import com.example.leon.domain.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
