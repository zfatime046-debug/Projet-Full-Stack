package com.example.demo.controllers;

import com.example.demo.dto.DocumentDTO;
import com.example.demo.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class DocumentController {

    private final DocumentService documentService;

    // POST /api/projets/{projetId}/documents
    @PostMapping("/api/projets/{projetId}/documents")
    public ResponseEntity<DocumentDTO> createDocument(
            @PathVariable Long projetId,
            @RequestBody DocumentDTO documentDTO
    ) throws IOException {
        return ResponseEntity.ok(
                documentService.createDocument(projetId, documentDTO, null)
        );
    }

    // GET /api/projets/{projetId}/documents
    @GetMapping("/api/projets/{projetId}/documents")
    public ResponseEntity<List<DocumentDTO>> getDocumentsByProjet(
            @PathVariable Long projetId) {
        return ResponseEntity.ok(
                documentService.getDocumentsByProjet(projetId));
    }

    // GET /api/documents/{id}
    @GetMapping("/api/documents/{id}")
    public ResponseEntity<DocumentDTO> getDocumentById(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }

    // PUT /api/documents/{id}
    @PutMapping("/api/documents/{id}")
    public ResponseEntity<DocumentDTO> updateDocument(
            @PathVariable Long id,
            @RequestBody DocumentDTO documentDTO
    ) throws IOException {
        return ResponseEntity.ok(
                documentService.updateDocument(id, documentDTO, null)
        );
    }

    // DELETE /api/documents/{id}
    @DeleteMapping("/api/documents/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id)
            throws IOException {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/documents/{id}/download
    @GetMapping("/api/documents/{id}/download")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id)
            throws MalformedURLException {
        Resource resource = documentService.downloadDocument(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}