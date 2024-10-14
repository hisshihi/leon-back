package com.example.leon.controllers;

import com.example.leon.domain.entities.BlackListPhone;
import com.example.leon.repositories.BlackListPhoneRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/admin-ui/blackListPhones")
@RequiredArgsConstructor
public class BlackListPhoneController {

    private final BlackListPhoneRepository blackListPhoneRepository;

    private final ObjectMapper objectMapper;

    @GetMapping
    public Page<BlackListPhone> getList(Pageable pageable) {
        return blackListPhoneRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public BlackListPhone getOne(@PathVariable Long id) {
        Optional<BlackListPhone> blackListPhoneOptional = blackListPhoneRepository.findById(id);
        return blackListPhoneOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    @GetMapping("/by-ids")
    public List<BlackListPhone> getMany(@RequestParam List<Long> ids) {
        return blackListPhoneRepository.findAllById(ids);
    }

    @PostMapping
    public BlackListPhone create(@RequestBody BlackListPhone blackListPhone) {
        return blackListPhoneRepository.save(blackListPhone);
    }

    @PatchMapping("/{id}")
    public BlackListPhone patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        BlackListPhone blackListPhone = blackListPhoneRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(blackListPhone).readValue(patchNode);

        return blackListPhoneRepository.save(blackListPhone);
    }

    @PatchMapping
    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) throws IOException {
        Collection<BlackListPhone> blackListPhones = blackListPhoneRepository.findAllById(ids);

        for (BlackListPhone blackListPhone : blackListPhones) {
            objectMapper.readerForUpdating(blackListPhone).readValue(patchNode);
        }

        List<BlackListPhone> resultBlackListPhones = blackListPhoneRepository.saveAll(blackListPhones);
        return resultBlackListPhones.stream()
                .map(BlackListPhone::getId)
                .toList();
    }

    @DeleteMapping("/{id}")
    public BlackListPhone delete(@PathVariable Long id) {
        BlackListPhone blackListPhone = blackListPhoneRepository.findById(id).orElse(null);
        if (blackListPhone != null) {
            blackListPhoneRepository.delete(blackListPhone);
        }
        return blackListPhone;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        blackListPhoneRepository.deleteAllById(ids);
    }
}
