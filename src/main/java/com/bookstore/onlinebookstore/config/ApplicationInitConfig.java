package com.bookstore.onlinebookstore.config;

import com.bookstore.onlinebookstore.enums.Role;
import com.bookstore.onlinebookstore.entity.User;
import com.bookstore.onlinebookstore.respository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args ->
        {
            if (userRepository.findByUsername("admin").isEmpty()) {
                var role = Role.ADMIN.name();
                User user = User
                        .builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role(role)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
