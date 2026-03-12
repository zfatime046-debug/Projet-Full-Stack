package com.example.demo.repositories;

import com.example.demo.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
    Optional<Employe> findByMatricule(String matricule);
    Optional<Employe> findByLogin(String login);
    Optional<Employe> findByEmail(String email);
}