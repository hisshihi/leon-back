package com.example.leon.services.impl;

import com.example.leon.domain.entities.Masters;
import com.example.leon.repositories.MastersRepository;
import com.example.leon.services.MastersService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Optional<Masters> findById(Long masterId) {
        return Optional.ofNullable(mastersRepository.findById(masterId).orElseThrow(() -> new RuntimeException("Мастер не найден")));
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

    @Override
    public Masters updateMaster(Long id, Masters masters) {
        Optional<Masters> optionalMasters = mastersRepository.findById(id);
        if (!optionalMasters.isPresent()) throw new EntityNotFoundException("Мастер с id " + id + " не найден");

        Masters existingMaster = optionalMasters.get();

        // Обновляем только те поля, которые были переданы
        if (masters.getFirstName() != null) existingMaster.setFirstName(masters.getFirstName());
        if (masters.getLastName() != null) existingMaster.setLastName(masters.getLastName());
        if (masters.getPost() != null) existingMaster.setPost(masters.getPost());
        if (masters.getPhone() != null) existingMaster.setPhone(masters.getPhone());
        if (masters.getTelegram() != null) existingMaster.setTelegram(masters.getTelegram());
        if (masters.getInst() != null) existingMaster.setInst(masters.getInst());
        if (masters.getUsername() != null) existingMaster.setUserName(masters.getUsername());
        if (masters.getImage() != null) existingMaster.setImage(masters.getImage());

        return mastersRepository.save(existingMaster);
    }
}
