package com.example.leon.services;

import com.example.leon.domain.entities.Masters;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MastersService {
    Masters create(Masters masters);

    List<Masters> findAll();

    Masters findById(Long masterId);

    UserDetailsService masterDetailService();
}
