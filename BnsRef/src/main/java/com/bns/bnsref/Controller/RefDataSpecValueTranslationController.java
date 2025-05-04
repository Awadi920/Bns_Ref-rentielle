package com.bns.bnsref.Controller;


import com.bns.bnsref.dto.RefDataSpecValueTranslationDTO;
import com.bns.bnsref.Service.RefDataSpecValueTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ref-data-spec-value-translations")
@RequiredArgsConstructor
public class RefDataSpecValueTranslationController {

    private final RefDataSpecValueTranslationService service;

    @PostMapping("/add")
    public ResponseEntity<RefDataSpecValueTranslationDTO> create(@RequestBody RefDataSpecValueTranslationDTO dto) {
        RefDataSpecValueTranslationDTO created = service.createRefDataSpecValueTranslation(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}") // Correction du chemin (de "upadate" à "update")
    public ResponseEntity<RefDataSpecValueTranslationDTO> update(@PathVariable Long id, @RequestBody RefDataSpecValueTranslationDTO dto) {
        RefDataSpecValueTranslationDTO updated = service.updateRefDataSpecValueTranslation(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefDataSpecValueTranslationDTO> getById(@PathVariable Long id) {
        RefDataSpecValueTranslationDTO translation = service.getRefDataSpecValueTranslationById(id);
        return ResponseEntity.ok(translation);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RefDataSpecValueTranslationDTO>> getAll() {
        List<RefDataSpecValueTranslationDTO> translations = service.getAllRefDataSpecValueTranslations();
        return ResponseEntity.ok(translations);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteRefDataSpecValueTranslation(id);
        return ResponseEntity.ok().build();
    }

}
