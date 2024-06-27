package com.example.leon.repositories;

import com.example.leon.domain.entities.ImageForMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageForMasterRepository extends JpaRepository<ImageForMaster, Long> {
}
