package com.example.leon.services;

import com.example.leon.domain.entities.Masters;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface MastersService {
    Masters create(Masters masters);

    List<Masters> findAll();

    Optional<Masters> findById(Long masterId);

    UserDetailsService masterDetailService();

    Masters updateMaster(Long id, Masters masters);
}
