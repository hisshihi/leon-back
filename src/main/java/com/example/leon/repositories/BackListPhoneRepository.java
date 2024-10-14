package com.example.leon.repositories;

import com.example.leon.domain.entities.BlackListPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackListPhoneRepository extends JpaRepository<BlackListPhone, Long> {
}
