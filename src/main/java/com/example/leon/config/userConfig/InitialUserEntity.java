package com.example.leon.config.userConfig;

import com.example.leon.domain.entities.Masters;
import com.example.leon.domain.entities.Role;
import com.example.leon.repositories.MastersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitialUserEntity implements CommandLineRunner {

    private final MastersRepository mastersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Masters admin = new Masters();
        admin.setRole(Role.ADMIN);
        admin.setFirstName("Елена");
        admin.setLastName("Львова");
        admin.setPost("Мастер технолог");
        admin.setPassword(passwordEncoder.encode("XlIxWubZ"));
        admin.setUserName("admin");

        Optional<Masters> masterAdmin = mastersRepository.findByUserName(admin.getUsername());
        if (masterAdmin.isEmpty()) {
            mastersRepository.save(admin);
            log.info("Администратор успешно создан и сохранён.");
        } else {
            log.info("Администратор уже существует");
        }
    }
}
