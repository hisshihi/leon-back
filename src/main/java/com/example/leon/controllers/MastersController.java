package com.example.leon.controllers;

import com.example.leon.domain.entities.Masters;
import com.example.leon.services.MastersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "masters")
@RequiredArgsConstructor
public class MastersController {

    private final MastersService mastersService;

    @PostMapping
    private ResponseEntity<Void> createMasters(@RequestBody Masters masters) {
        mastersService.create(masters);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
