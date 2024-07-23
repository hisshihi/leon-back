package com.example.leon.services;

import com.example.leon.domain.entities.HomeCare;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface HomeCareService {

    @CacheEvict(value = "homeCare", allEntries = true)
    @CachePut(value = "homeCare", key = "homeCare.id")
    void save(HomeCare homeCare);

    @Cacheable("homeCare")
    List<HomeCare> findAllHomeCares();

    @CacheEvict(value = "homeCare", allEntries = true)
    void deleteHomeCare(Long id);
}
