package com.example.demo.repositories;

import com.example.demo.entities.Profil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilRepository extends JpaRepository<Profil, Long> {
    Profil findByCode(String code);
}