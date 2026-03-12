package com.example.demo.repositories;

import com.example.demo.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByProjetId(Long projetId);
}