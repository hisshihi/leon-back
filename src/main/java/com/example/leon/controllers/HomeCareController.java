package com.example.leon.controllers;

import com.example.leon.domain.entities.HomeCare;
import com.example.leon.services.HomeCareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("home-care")
@RequiredArgsConstructor
public class HomeCareController {

    private final HomeCareService homeCareService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> create(
            @RequestParam("image1") MultipartFile image1,
            @RequestParam("image2") MultipartFile image2,
            @RequestParam("text") String text
            ) {

        try {
            HomeCare homeCare = HomeCare.builder()
                    .image1(image1.getBytes())
                    .image2(image2.getBytes())
                    .text(text)
                    .build();

            homeCareService.save(homeCare);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    private ResponseEntity<List<HomeCare>> getHomeCares() {
        List<HomeCare> homeCares = homeCareService.findAllHomeCares();
        return ResponseEntity.ok(homeCares);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteHomeCare(@PathVariable Long id) {
        homeCareService.deleteHomeCare(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
