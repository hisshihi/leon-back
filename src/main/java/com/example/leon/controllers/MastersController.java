package com.example.leon.controllers;

import com.example.leon.domain.dto.MasterDto;
import com.example.leon.domain.entities.Masters;
import com.example.leon.domain.entities.Role;
import com.example.leon.mappers.impl.MasterMapper;
import com.example.leon.services.MastersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "masters")
@RequiredArgsConstructor
public class MastersController {

    private final MastersService mastersService;
    private final MasterMapper masterMapper;

    @PostMapping
    private ResponseEntity<Void> createMasters(
            @RequestParam("file") MultipartFile file,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("post") String post,
            @RequestParam("phone") String phone,
            @RequestParam("telegram") String telegram,
            @RequestParam("inst") String inst,
            @RequestParam("userName") String userName,
            @RequestParam("password") String password
            ) {

        try {
            Masters master = Masters.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .post(post)
                    .phone(phone)
                    .telegram(telegram)
                    .inst(inst)
                    .image(file.getBytes())
                    .userName(userName)
                    .password(password)
                    .role(Role.MASTER)
                    .build();

            mastersService.create(master);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    private ResponseEntity<List<MasterDto>> listMasters() {
        List<Masters> masters = mastersService.findAll();
        List<MasterDto> masterDtoList = masters.stream().map(masterMapper::mapTo).toList();
        return new ResponseEntity<>(masterDtoList, HttpStatus.OK);
    }

}
