package com.example.leon.services;

import com.example.leon.domain.entities.HomeCare;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface HomeCareService {

    @CacheEvict(value = "home-care", allEntries = true)
    @CachePut(value = "home-care", key = "#homeCare.id")
    void save(HomeCare homeCare);

    @Cacheable("home-care")
    List<HomeCare> findAllHomeCares();

    @CacheEvict(value = "home-care", allEntries = true)
    void deleteHomeCare(Long id);
}
