package com.kohub.coworking.config;

import com.kohub.coworking.model.*;
import com.kohub.coworking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Initialisation des utilisateurs
        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setUsername("Rein");
            user1.setPassword(passwordEncoder.encode("rein"));
            user1.setNumber("0349049881");
            user1.setRole(Role.ROLE_USER);
            userRepository.save(user1);

            User user2 = new User();
            user2.setUsername("John");
            user2.setPassword(passwordEncoder.encode("john"));
            user2.setNumber("0381034567");
            user2.setRole(Role.ROLE_USER);
            userRepository.save(user2);

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setNumber("0000000001");
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
        }
    }
} 