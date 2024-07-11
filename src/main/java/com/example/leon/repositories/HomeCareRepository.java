package com.example.leon.repositories;

import com.example.leon.domain.entities.HomeCare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeCareRepository extends JpaRepository<HomeCare, Long> {
}
