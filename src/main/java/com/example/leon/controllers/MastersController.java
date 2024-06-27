package com.example.leon.controllers;

import com.example.leon.domain.entities.Masters;
import com.example.leon.services.MastersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "masters")
@RequiredArgsConstructor
public class MastersController {

    private final MastersService mastersService;

    @PostMapping
    private ResponseEntity<Void> createMasters(
            @RequestParam("file") MultipartFile file,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("telegram") String telegram,
            @RequestParam("inst") String inst
            ) {

        try {
            Masters master = Masters.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .phone(phone)
                    .telegram(telegram)
                    .inst(inst)
                    .image(file.getBytes())
                    .build();

            mastersService.create(master);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    private ResponseEntity<List<Masters>> listMasters() {
        List<Masters> masters = mastersService.findAll();
        return new ResponseEntity<>(masters, HttpStatus.OK);
    }

}
