package com.example.leon.services;

import com.example.leon.domain.entities.Masters;

import java.util.List;

public interface MastersService {
    Masters create(Masters masters);

    List<Masters> findAll();
}
