package com.example.leon.services;

import com.example.leon.domain.entities.Masters;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface MastersService {

    @CacheEvict(value = "master", allEntries = true)
    @CachePut(value = "master", key = "#masters.id")
    Masters create(Masters masters);

    @Cacheable("master")
    List<Masters> findAll();

    @Cacheable("master")
    Optional<Masters> findById(Long masterId);

    UserDetailsService masterDetailService();

    @CacheEvict(value = "master", allEntries = true)
    @CachePut(value = "master", key = "#id")
    Masters updateMaster(Long id, Masters masters);

    @CacheEvict(value = "master", allEntries = true)
    void deleteMaster(Optional<Masters> optionalMasters);
}
