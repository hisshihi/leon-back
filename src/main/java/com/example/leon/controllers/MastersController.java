package com.example.leon.controllers;

import com.example.leon.domain.entities.ImageForMaster;
import com.example.leon.domain.entities.Masters;
import com.example.leon.services.ImageForMasterService;
import com.example.leon.services.MastersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "masters")
@RequiredArgsConstructor
public class MastersController {

    private final MastersService mastersService;
    private final ImageForMasterService imageForMasterService;

    @PostMapping
    private ResponseEntity<Void> createMasters(@RequestBody Masters masters) {
        mastersService.create(masters);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Сохранение фотографии мастера
    @PostMapping(path = "/{masterId}/image")
    private ResponseEntity<Void> uploadImage(
            @PathVariable Long masterId,
            @RequestParam("image")MultipartFile image
            ) {
        try {
            if (!image.isEmpty()) {
                byte[] imageBytes = image.getBytes();
                ImageForMaster imageForMaster = new ImageForMaster();
                imageForMaster.setImageMaster(imageBytes);

//                Обновляем изображение у мастера
                Masters masters = mastersService.findById(masterId);

                masters.setImageForMaster(imageForMaster);
                imageForMaster.setMaster(masters);

                mastersService.create(masters);

                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    private ResponseEntity<List<Masters>> listMasters() {
        List<Masters> masters = mastersService.findAll();
        return new ResponseEntity<>(new ArrayList<>(masters), HttpStatus.OK);
    }

}
