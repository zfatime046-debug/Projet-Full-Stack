package com.example.demo.services;

import com.example.demo.dto.DocumentDTO;
import com.example.demo.entities.Document;
import com.example.demo.entities.Projet;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.mappers.DocumentMapper;
import com.example.demo.repositories.DocumentRepository;
import com.example.demo.repositories.ProjetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final ProjetRepository projetRepository;
    private final DocumentMapper documentMapper;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

   
    public DocumentDTO createDocument(Long projetId,
                                      DocumentDTO documentDTO,
                                      MultipartFile file) throws IOException {

        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Projet non trouvé avec l'id : " + projetId));

        Document document = documentMapper.toEntity(documentDTO);
        document.setProjet(projet);

        if (file != null && !file.isEmpty()) {
            String chemin = saveFile(file, projetId);
            document.setChemin(chemin);
        }

        return documentMapper.toDTO(documentRepository.save(document));
    }

    
    public List<DocumentDTO> getDocumentsByProjet(Long projetId) {
        if (!projetRepository.existsById(projetId))
            throw new ResourceNotFoundException(
                    "Projet non trouvé avec l'id : " + projetId);

        return documentRepository.findByProjetId(projetId)
                .stream()
                .map(documentMapper::toDTO)
                .collect(Collectors.toList());
    }

    
    public DocumentDTO getDocumentById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document non trouvé avec l'id : " + id));
        return documentMapper.toDTO(document);
    }

    
    public DocumentDTO updateDocument(Long id,
                                      DocumentDTO documentDTO,
                                      MultipartFile file) throws IOException {

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document non trouvé avec l'id : " + id));

        document.setCode(documentDTO.getCode());
        document.setLibelle(documentDTO.getLibelle());
        document.setDescription(documentDTO.getDescription());

        if (file != null && !file.isEmpty()) {
            if (document.getChemin() != null) deleteFile(document.getChemin());
            document.setChemin(saveFile(file, document.getProjet().getId()));
        }

        return documentMapper.toDTO(documentRepository.save(document));
    }

    
    public void deleteDocument(Long id) throws IOException {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document non trouvé avec l'id : " + id));

        if (document.getChemin() != null) deleteFile(document.getChemin());
        documentRepository.delete(document);
    }

    
    public Resource downloadDocument(Long id) throws MalformedURLException {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document non trouvé avec l'id : " + id));

        if (document.getChemin() == null)
            throw new ResourceNotFoundException(
                    "Aucun fichier associé au document avec l'id : " + id);

        Path filePath = Paths.get(document.getChemin()).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists())
            throw new ResourceNotFoundException(
                    "Fichier introuvable : " + document.getChemin());

        return resource;
    }

    
    private String saveFile(MultipartFile file, Long projetId) throws IOException {
        Path uploadPath = Paths.get(uploadDir, "projets", projetId.toString());
        Files.createDirectories(uploadPath);

        String original  = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = original.contains(".")
                ? original.substring(original.lastIndexOf(".")) : "";
        String newName   = UUID.randomUUID() + extension;

        Path target = uploadPath.resolve(newName);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return target.toString();
    }

    private void deleteFile(String chemin) throws IOException {
        Files.deleteIfExists(Paths.get(chemin));
    }
}
