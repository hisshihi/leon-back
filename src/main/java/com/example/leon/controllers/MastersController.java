package com.example.leon.controllers;

import com.example.leon.domain.dto.MasterDto;
import com.example.leon.domain.dto.MasterDtoSmall;
import com.example.leon.domain.entities.Masters;
import com.example.leon.domain.entities.Role;
import com.example.leon.mappers.impl.MasterMapper;
import com.example.leon.mappers.impl.MasterSmallMapper;
import com.example.leon.services.MastersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "masters")
@RequiredArgsConstructor
public class MastersController {

    private final MastersService mastersService;
    private final MasterMapper masterMapper;
    private final PasswordEncoder passwordEncoder;
    private final MasterSmallMapper masterSmallMapper;

    @PostMapping
    private ResponseEntity<Masters> createMasters(
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
                    .password(passwordEncoder.encode(password))
                    .role(Role.MASTER)
                    .build();

            mastersService.create(master);
            return new ResponseEntity<>(master, HttpStatus.CREATED);
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

//    Быстрый вывод мастеров
    @GetMapping("/small")
    private ResponseEntity<List<MasterDtoSmall>> listLittleMasters() {
        List<Masters> masters = mastersService.findAll();
        List<MasterDtoSmall> masterDtoSmalls = masters.stream().map(masterSmallMapper::mapTo).collect(Collectors.toList());
        return new ResponseEntity<>(masterDtoSmalls, HttpStatus.OK);
    }

//    Вывод всех мастеров для админа
    @GetMapping("/admin")
    private ResponseEntity<List<Masters>> listMastersAdmin() {
        List<Masters> masters = mastersService.findAll();
        return ResponseEntity.ok(masters);
    }

    @PatchMapping(path = "/admin/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<Masters> pathMaster(
            @PathVariable(value = "id", required = false) Long id,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "post", required = false) String post,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "telegram", required = false) String telegram,
            @RequestParam(value = "inst", required = false) String inst,
            @RequestParam(value = "username", required = false) String userName,
            @RequestParam(value = "image", required = false) MultipartFile file,
            @RequestParam(value = "password", required = false) String password
    ) throws IOException {

        // Загрузка существующуего пользователя из бд
        Optional<Masters> optionalMasters = mastersService.findById(id);
        if (!optionalMasters.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Masters masters = optionalMasters.get();

        // Обновите только те поля, которые были переданы в запросе
        if (firstName != null) masters.setFirstName(firstName);
        if (lastName != null) masters.setLastName(lastName);
        if (post != null) masters.setPost(post);
        if (phone != null) masters.setPhone(phone);
        if (telegram != null) masters.setTelegram(telegram);
        if (inst != null) masters.setInst(inst);
        if (userName != null) masters.setUserName(userName);
        if (file != null && !file.isEmpty()) masters.setImage(file.getBytes());
        if (password != null) masters.setPassword(passwordEncoder.encode(password));

        mastersService.updateMaster(id, masters);
        return ResponseEntity.ok(masters);

    }

    // Удаление мастера
    @DeleteMapping("/admin/{id}")
    private ResponseEntity<Void> deleteMaster(@PathVariable Long id) {
        Optional<Masters> optionalMasters = mastersService.findById(id);
        if (!optionalMasters.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        mastersService.deleteMaster(optionalMasters);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
