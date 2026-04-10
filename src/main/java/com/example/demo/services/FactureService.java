package com.example.demo.services;

import com.example.demo.dto.FactureDTO;
import com.example.demo.entities.Facture;
import com.example.demo.entities.Phase;
import com.example.demo.entities.StatutFacture;
import com.example.demo.repositories.FactureRepository;
import com.example.demo.repositories.PhaseRepository;
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

        if (phase.getEtatRealisation() == null || !phase.getEtatRealisation()) {
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

        phase.setEtatFacturation(true);
        phaseRepository.save(phase);

        return toDTO(factureRepository.save(facture));
    }

    public List<FactureDTO> getAllFactures() {
        return factureRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
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

        if (dto.getMontant() == null || dto.getMontant().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant doit être supérieur à 0.");
        }

        facture.setCode(dto.getCode());
        facture.setDateFacture(dto.getDateFacture());
        facture.setMontant(dto.getMontant());

        if (dto.getStatut() != null && !dto.getStatut().isBlank()) {
            facture.setStatut(StatutFacture.valueOf(dto.getStatut()));
        }

        Facture saved = factureRepository.save(facture);

        Phase phase = saved.getPhase();
        if (phase != null) {
            if (saved.getStatut() == StatutFacture.PAYEE) {
                phase.setEtatPaiement(true);
            } else {
                phase.setEtatPaiement(false);
            }
            phaseRepository.save(phase);
        }

        return toDTO(saved);
    }

    public void deleteFacture(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));

        if (facture.getStatut() == StatutFacture.PAYEE) {
            throw new RuntimeException("Impossible de supprimer une facture payée.");
        }

        Phase phase = facture.getPhase();
        if (phase != null) {
            phase.setEtatFacturation(false);
            phase.setEtatPaiement(false);
            phaseRepository.save(phase);
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

        if (f.getPhase() != null) {
            dto.setPhaseId(f.getPhase().getId());
            dto.setPhaseNom(f.getPhase().getLibelle());

            dto.setEtatFacturationLabel(
                    Boolean.TRUE.equals(f.getPhase().getEtatFacturation())
                            ? "facturée"
                            : "non facturée"
            );

            dto.setEtatPaiementLabel(
                    Boolean.TRUE.equals(f.getPhase().getEtatPaiement())
                            ? "payée"
                            : "non payée"
            );

            dto.setEtatRealisationLabel(
                    Boolean.TRUE.equals(f.getPhase().getEtatRealisation())
                            ? "terminée"
                            : "non terminée"
            );
        }


        return dto;
    }
    public FactureDTO updateStatut(Long id, FactureDTO dto) {

        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));

        if (dto.getStatut() == null || dto.getStatut().isBlank()) {
            throw new RuntimeException("Statut obligatoire");
        }

        facture.setStatut(StatutFacture.valueOf(dto.getStatut()));

        return toDTO(factureRepository.save(facture));
    }
}