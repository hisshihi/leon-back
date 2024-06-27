package com.example.leon.services.impl;

import com.example.leon.domain.entities.Masters;
import com.example.leon.repositories.MastersRepository;
import com.example.leon.services.MastersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MastersServiceImpl implements MastersService {

    private final MastersRepository mastersRepository;

    @Override
    public Masters create(Masters masters) {
        return mastersRepository.save(masters);
    }

    @Override
    public List<Masters> findAll() {
        return StreamSupport.stream(mastersRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Masters findById(Long masterId) {
        return mastersRepository.findById(masterId).orElseThrow(() -> new RuntimeException("Мастер не найден"));
    }
}
