package com.bns.bnsref.Controller;


import com.bns.bnsref.dto.RefDataValueTranslationDTO;
import com.bns.bnsref.Service.RefDataValueTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/ref-data-value-translations")
@RequiredArgsConstructor
public class RefDataValueTranslationController {

    private final RefDataValueTranslationService service;

    @PostMapping("/add")
    public ResponseEntity<RefDataValueTranslationDTO> create(@RequestBody RefDataValueTranslationDTO dto) {
        RefDataValueTranslationDTO created = service.createRefDataValueTranslation(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RefDataValueTranslationDTO> update(@PathVariable Long id, @RequestBody RefDataValueTranslationDTO dto) {
        RefDataValueTranslationDTO updated = service.updateRefDataValueTranslation(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefDataValueTranslationDTO> getById(@PathVariable Long id) {
        RefDataValueTranslationDTO translation = service.getRefDataValueTranslationById(id);
        return ResponseEntity.ok(translation);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RefDataValueTranslationDTO>> getAll() {
        List<RefDataValueTranslationDTO> translations = service.getAllRefDataValueTranslations();
        return ResponseEntity.ok(translations);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteRefDataValueTranslation(id);
        return ResponseEntity.ok().build();
    }
}
