package com.example.leon.services.impl;

import com.example.leon.domain.entities.Masters;
import com.example.leon.repositories.MastersRepository;
import com.example.leon.services.MastersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MastersServiceImpl implements MastersService {

    private final MastersRepository mastersRepository;

    @Override
    public Masters create(Masters masters) {
        return mastersRepository.save(masters);
    }

    @Override
    public List<Masters> findAll() {
        return StreamSupport.stream(mastersRepository.findAll().spliterator(), false)
                .filter(masters -> !masters.getUsername().equals("admin"))
                .collect(Collectors.toList());
    }

    @Override
    public Masters findById(Long masterId) {
        return mastersRepository.findById(masterId).orElseThrow(() -> new RuntimeException("Мастер не найден"));
    }

    @Override
    // UserDetailsService - интерфейс, предоставляющий метод для загрузки пользовательских данных по имени пользователя.
    public UserDetailsService masterDetailService() {
        return new UserDetailsService() {
            @Override
            /*
            * loadUserByUsername(String username) - метод, который пытается найти пользователя в mastersRepository по имени пользователя.
            * Если пользователь не найден, выбрасывается исключение UsernameNotFoundException.
            * */
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return mastersRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Мастер не найден"));
            }
        };
    }
}
