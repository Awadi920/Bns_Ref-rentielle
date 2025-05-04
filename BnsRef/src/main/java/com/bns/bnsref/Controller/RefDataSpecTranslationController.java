package com.bns.bnsref.Controller;


import com.bns.bnsref.dto.RefDataSpecTranslationDTO;
import com.bns.bnsref.Service.RefDataSpecTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ref-data-spec-translations")
@RequiredArgsConstructor

public class RefDataSpecTranslationController {

    private final RefDataSpecTranslationService service;

    @PostMapping("/add")
    public ResponseEntity<RefDataSpecTranslationDTO> create(@RequestBody RefDataSpecTranslationDTO dto) {
        RefDataSpecTranslationDTO created = service.createRefDataSpecTranslation(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RefDataSpecTranslationDTO> update(@PathVariable Long id, @RequestBody RefDataSpecTranslationDTO dto) {
        RefDataSpecTranslationDTO updated = service.updateRefDataSpecTranslation(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefDataSpecTranslationDTO> getById(@PathVariable Long id) {
        RefDataSpecTranslationDTO translation = service.getRefDataSpecTranslationById(id);
        return ResponseEntity.ok(translation);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RefDataSpecTranslationDTO>> getAll() {
        List<RefDataSpecTranslationDTO> translations = service.getAllRefDataSpecTranslations();
        return ResponseEntity.ok(translations);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteRefDataSpecTranslation(id);
        return ResponseEntity.ok().build();
    }

}
