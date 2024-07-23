package com.example.leon.services.impl;

import com.example.leon.domain.entities.HomeCare;
import com.example.leon.repositories.HomeCareRepository;
import com.example.leon.services.HomeCareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeCareServiceImpl implements HomeCareService {

    private final HomeCareRepository homeCareRepository;

    @Override
    public void save(HomeCare homeCare) {
        homeCareRepository.save(homeCare);
    }

    @Override
    public List<HomeCare> findAllHomeCares() {
        return homeCareRepository.findAll();
    }

    @Override
    public void deleteHomeCare(Long id) {
        homeCareRepository.deleteById(id);
    }
}
