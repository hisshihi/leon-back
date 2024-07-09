package com.example.leon.repositories;

import com.example.leon.domain.entities.Earnings;
import com.example.leon.domain.entities.Masters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EarningRepository extends JpaRepository<Earnings, Long> {

    Optional<Earnings> findByMasterId(Long id);

    @Query("SELECT SUM(e.totalEarnings) FROM Earnings e")
    int findTotalEarnings();

}
