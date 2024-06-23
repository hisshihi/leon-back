package com.example.leon.repositories;

import com.example.leon.domain.entities.Masters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MastersRepository extends JpaRepository<Masters, Long> {
}
