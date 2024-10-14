package com.example.leon.repositories;

import com.example.leon.domain.entities.BlackListPhone;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListPhoneRepository extends JpaRepository<BlackListPhone, Long> {
}
