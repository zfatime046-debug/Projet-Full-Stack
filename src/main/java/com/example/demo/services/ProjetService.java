package com.example.demo.services;

import com.example.demo.dto.ProjetRequest;
import com.example.demo.entities.Projet;

import java.util.List;

public interface ProjetService {

    Projet createProjet(ProjetRequest request);

    Projet updateProjet(Long id, ProjetRequest request);

    Projet getProjetById(Long id);

    List<Projet> getAllProjets();

    List<Projet> getProjetsByOrganisme(Long organismeId);

    List<Projet> getProjetsByChefDeProjet(Long employeId);

    void deleteProjet(Long id);
}
