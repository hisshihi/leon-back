package com.example.leon.services.impl;

import com.example.leon.domain.entities.Masters;
import com.example.leon.repositories.MastersRepository;
import com.example.leon.services.MastersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MastersServiceImpl implements MastersService {

    private final MastersRepository mastersRepository;

    @Override
    public Masters create(Masters masters) {
        return mastersRepository.save(masters);
    }
}
