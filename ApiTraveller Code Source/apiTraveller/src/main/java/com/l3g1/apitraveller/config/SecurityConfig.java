package com.l3g1.apitraveller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class SecurityConfig {
    // Configuration class for security-related settings.
    @Bean
    // Returns a BCryptPasswordEncoder instance for encoding passwords using BCrypt hashing algorithm.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}