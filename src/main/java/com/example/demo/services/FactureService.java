package com.example.demo.services;

import com.example.demo.dto.FactureDTO;
import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactureService {

    private final FactureRepository factureRepository;
    private final PhaseRepository phaseRepository;

    public FactureDTO createFacture(Long phaseId, FactureDTO dto) {
        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new RuntimeException("Phase introuvable"));

        if (!"TERMINE".equals(phase.getEtatRealisation())) {
            throw new RuntimeException("La phase doit être terminée pour être facturée.");
        }

        if (factureRepository.existsByPhaseId(phaseId)) {
            throw new RuntimeException("Cette phase a déjà été facturée.");
        }
        if (dto.getMontant() == null || dto.getMontant().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant doit être supérieur à 0.");
        }

        Facture facture = new Facture();
        facture.setCode(dto.getCode());
        facture.setDateFacture(dto.getDateFacture());
        facture.setMontant(dto.getMontant());
        facture.setStatut(StatutFacture.EMISE);
        facture.setPhase(phase);

        phase.setEtatFacturation("FACTUREE");
        phaseRepository.save(phase);

        return toDTO(factureRepository.save(facture));
    }

    public List<FactureDTO> getAllFactures() {
        return factureRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public FactureDTO getFactureById(Long id) {
        return toDTO(factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable")));
    }

    public FactureDTO updateFacture(Long id, FactureDTO dto) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));

        if (facture.getStatut() == StatutFacture.PAYEE) {
            throw new RuntimeException("Impossible de modifier une facture déjà payée.");
        }

        facture.setCode(dto.getCode());
        facture.setDateFacture(dto.getDateFacture());
        facture.setMontant(dto.getMontant());
        facture.setStatut(StatutFacture.valueOf(dto.getStatut()));

        return toDTO(factureRepository.save(facture));
    }

    public void deleteFacture(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));
        if (facture.getStatut() == StatutFacture.PAYEE) {
            throw new RuntimeException("Impossible de supprimer une facture payée.");
        }
        factureRepository.deleteById(id);
    }

    private FactureDTO toDTO(Facture f) {
        FactureDTO dto = new FactureDTO();
        dto.setId(f.getId());
        dto.setCode(f.getCode());
        dto.setDateFacture(f.getDateFacture());
        dto.setMontant(f.getMontant());
        dto.setStatut(f.getStatut() != null ? f.getStatut().name() : "EMISE");
        dto.setPhaseId(f.getPhase().getId());
        dto.setPhaseNom(f.getPhase().getLibelle());
        return dto;
    }
}