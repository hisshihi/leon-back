package com.example.leon.services.impl;

import com.example.leon.domain.entities.ImageForMaster;
import com.example.leon.repositories.ImageForMasterRepository;
import com.example.leon.services.ImageForMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageForMasterServiceImpl implements ImageForMasterService {

    private final ImageForMasterRepository imageForMasterRepository;


    @Override
    public void create(ImageForMaster imageForMaster) {
        imageForMasterRepository.save(imageForMaster);
    }
}
