package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
<<<<<<< HEAD
import org.springframework.security.web.SecurityFilterChain;

@Configuration
=======
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
>>>>>>> 1bf0ea81ecc529aa4c6df3392d3d134165e15cfe
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
<<<<<<< HEAD

=======
>>>>>>> 1bf0ea81ecc529aa4c6df3392d3d134165e15cfe
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
<<<<<<< HEAD

=======
>>>>>>> 1bf0ea81ecc529aa4c6df3392d3d134165e15cfe
        return http.build();
    }
}