package com.example.demo.repositories;

import com.example.demo.entities.Organisme;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrganismeRepository extends JpaRepository<Organisme, Long> {
    List<Organisme> findByNomContaining(String nom);
    Organisme findByCode(String code);
    List<Organisme> findByNomContactContaining(String nomContact);
}