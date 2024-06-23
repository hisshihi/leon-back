package com.example.leon.controllers;

import com.example.leon.domain.entities.Masters;
import com.example.leon.services.MastersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    private ResponseEntity<List<Masters>> listMasters() {
        List<Masters> masters = mastersService.findAll();
        return new ResponseEntity<>(new ArrayList<>(masters), HttpStatus.OK);
    }

}
