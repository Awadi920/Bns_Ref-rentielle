package com.bns.bnsref.Controller;


import com.bns.bnsref.dto.RefDataTranslationDTO;
import com.bns.bnsref.Service.RefDataTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ref-data-translations")
@RequiredArgsConstructor
public class RefDataTranslationController {

    private final RefDataTranslationService service;


    @PostMapping("/add")
    public ResponseEntity<RefDataTranslationDTO> create(@RequestBody RefDataTranslationDTO dto) {
        RefDataTranslationDTO created = service.createRefDataTranslation(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RefDataTranslationDTO> update(@PathVariable Long id, @RequestBody RefDataTranslationDTO dto) {
        RefDataTranslationDTO updated = service.updateRefDataTranslation(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefDataTranslationDTO> getById(@PathVariable Long id) {
        RefDataTranslationDTO translation = service.getRefDataTranslationById(id);
        return ResponseEntity.ok(translation);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RefDataTranslationDTO>> getAll() {
        List<RefDataTranslationDTO> translations = service.getAllRefDataTranslations();
        return ResponseEntity.ok(translations);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteRefDataTranslation(id);
        return ResponseEntity.ok().build();
    }
}