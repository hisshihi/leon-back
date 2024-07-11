package com.example.leon.services;

import com.example.leon.domain.entities.HomeCare;

import java.util.List;

public interface HomeCareService {
    void save(HomeCare homeCare);

    List<HomeCare> findAllHomeCares();
}
