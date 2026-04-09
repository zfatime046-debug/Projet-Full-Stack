package com.example.demo.config;

import com.example.demo.entities.Employe;
import com.example.demo.repositories.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeRepository employeRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Employe employe = employeRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable : " + login));

        String role = "USER";
        if (employe.getProfil() != null && employe.getProfil().getCode() != null) {
            role = employe.getProfil().getCode();
        }

        boolean enabled = employe.getActif() == null || employe.getActif();

        return User.builder()
                .username(employe.getLogin())
                .password(employe.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(role)))  // ← MODIFIÉ (supprimé ROLE_)
                .disabled(!enabled)
                .build();
    }
}