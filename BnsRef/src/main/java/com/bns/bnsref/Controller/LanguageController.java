package com.bns.bnsref.Controller;

import com.bns.bnsref.dto.LanguageDTO;
import com.bns.bnsref.Service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/languages")
@RequiredArgsConstructor
public class LanguageController {

    private final LanguageService languageService;

    @PostMapping("/add")
    public ResponseEntity<LanguageDTO> addLanguage(@RequestBody LanguageDTO languageDTO) {
        LanguageDTO createdLanguage = languageService.addLanguage(languageDTO);
        return ResponseEntity.status(201).body(createdLanguage);
    }

    @PutMapping("/update/{codeLanguage}")
    public ResponseEntity<LanguageDTO> updateLanguage(@PathVariable String codeLanguage, @RequestBody LanguageDTO languageDTO) {
        LanguageDTO updatedLanguage = languageService.updateLanguage(codeLanguage, languageDTO);
        return ResponseEntity.ok(updatedLanguage);
    }

    @DeleteMapping("/delete/{codeLanguage}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable String codeLanguage) {
        languageService.deleteLanguage(codeLanguage);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{codeLanguage}")
    public ResponseEntity<LanguageDTO> getLanguageById(@PathVariable String codeLanguage) {
        LanguageDTO languageDTO = languageService.getLanguageById(codeLanguage);
        return ResponseEntity.ok(languageDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LanguageDTO>> getAllLanguages() {
        List<LanguageDTO> languages = languageService.getAllLanguages();
        return ResponseEntity.ok(languages);
    }
}