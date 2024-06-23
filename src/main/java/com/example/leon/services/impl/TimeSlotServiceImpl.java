package com.example.leon.services.impl;

import com.example.leon.repositories.TimeSlotRepository;
import com.example.leon.services.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

}
