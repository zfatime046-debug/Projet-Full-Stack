package com.example.demo.services;

import com.example.demo.dto.LivrableRequestDTO;
import com.example.demo.dto.LivrableResponseDTO;
import com.example.demo.entities.Livrable;
import com.example.demo.entities.Phase;
import com.example.demo.mappers.LivrableMapper;
import com.example.demo.repositories.LivrableRepository;
import com.example.demo.repositories.PhaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LivrableService {

    private final LivrableRepository livrableRepository;
    private final PhaseRepository phaseRepository;
    private final LivrableMapper livrableMapper;

    public LivrableResponseDTO create(Long phaseId, LivrableRequestDTO dto) {
        Phase phase = phaseRepository.findById(phaseId)
                .orElseThrow(() -> new RuntimeException("Phase non trouvée : " + phaseId));
        Livrable livrable = livrableMapper.toEntity(dto);
        livrable.setPhase(phase);
        return livrableMapper.toDTO(livrableRepository.save(livrable));
    }

    public List<LivrableResponseDTO> getByPhase(Long phaseId) {
        return livrableRepository.findByPhaseId(phaseId)
                .stream()
                .map(livrableMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LivrableResponseDTO getById(Long id) {
        return livrableMapper.toDTO(
                livrableRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Livrable non trouvé : " + id))
        );
    }

    public LivrableResponseDTO update(Long id, LivrableRequestDTO dto) {
        Livrable livrable = livrableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livrable non trouvé : " + id));
        livrable.setCode(dto.getCode());
        livrable.setLibelle(dto.getLibelle());
        livrable.setDescription(dto.getDescription());
        livrable.setChemin(dto.getChemin());
        return livrableMapper.toDTO(livrableRepository.save(livrable));
    }

    public void delete(Long id) {
        livrableRepository.deleteById(id);
    }
}