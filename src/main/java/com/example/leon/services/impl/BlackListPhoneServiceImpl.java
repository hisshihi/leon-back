package com.example.leon.services.impl;

import com.example.leon.domain.entities.BlackListPhone;
import com.example.leon.repositories.BlackListPhoneRepository;
import com.example.leon.services.BlackListPhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlackListPhoneServiceImpl implements BlackListPhoneService {

    private final BlackListPhoneRepository blackListPhoneRepository;

    @Override
    public boolean phoneIsBlackList(String phone) {
        List<BlackListPhone> blackListPhones = blackListPhoneRepository.findAll();
        boolean isPhoneInBlackList = blackListPhones
                .stream()
                .anyMatch(blackListPhone -> blackListPhone.getPhone().equals(phone));
        return isPhoneInBlackList;
    }

}
