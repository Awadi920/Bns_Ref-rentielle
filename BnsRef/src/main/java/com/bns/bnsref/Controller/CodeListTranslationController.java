package com.bns.bnsref.Controller;


import com.bns.bnsref.dto.CodeListTranslationDTO;
import com.bns.bnsref.Service.CodeListTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/code-list-translations")
@RequiredArgsConstructor
public class CodeListTranslationController {

    private final CodeListTranslationService service;



    @PostMapping("/add")
    public ResponseEntity<CodeListTranslationDTO> create(@RequestBody CodeListTranslationDTO dto) {
        CodeListTranslationDTO created = service.createCodeListTranslation(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CodeListTranslationDTO> update(@PathVariable Long id, @RequestBody CodeListTranslationDTO dto) {
        CodeListTranslationDTO updated = service.updateCodeListTranslation(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CodeListTranslationDTO> getById(@PathVariable Long id) {
        CodeListTranslationDTO translation = service.getCodeListTranslationById(id);
        return ResponseEntity.ok(translation);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CodeListTranslationDTO>> getAll() {
        List<CodeListTranslationDTO> translations = service.getAllCodeListTranslations();
        return ResponseEntity.ok(translations);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCodeListTranslation(id);
        return ResponseEntity.ok().build();
    }
}