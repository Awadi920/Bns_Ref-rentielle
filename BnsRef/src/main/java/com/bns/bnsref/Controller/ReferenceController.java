package com.bns.bnsref.Controller;

import com.bns.bnsref.dto.ReferenceDTO;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Mappers.ReferenceMapper;
import com.bns.bnsref.Service.ReferenceService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/references")
@RequiredArgsConstructor

public class ReferenceController {


    private final ReferenceService referenceService;

    @GetMapping("/all")
    public ResponseEntity<List<ReferenceDTO>> getAllReferences(
            @RequestParam(required = false, defaultValue = "false") boolean includeTranslations,
            @RequestParam(required = false) String lang) {
        if (includeTranslations && lang != null) {
            return ResponseEntity.ok(referenceService.getAllReferencesWithLang(lang));
        }
        return ResponseEntity.ok(referenceService.getAllReferencesAsDTO());
    }

    @GetMapping("/all-with-translations")
    public ResponseEntity<List<ReferenceDTO>> getAllReferencesWithTranslations() {
        return ResponseEntity.ok(referenceService.getAllReferencesWithAllTranslations());
    }


    @GetMapping("/{codeList}")
    public ResponseEntity<ReferenceDTO> getReferenceWithLang(
            @PathVariable String codeList,
            @RequestParam(required = false) String lang) {
        if (lang != null) {
            return ResponseEntity.ok(referenceService.getReferenceWithLang(codeList, lang));
        }
        return ResponseEntity.ok(referenceService.getReferenceWithLang(codeList, null));
    }

    // Nouvel endpoint pour les détails complets avec toutes les traductions
    @GetMapping("/{codeList}/details")
    public ResponseEntity<ReferenceDTO> getReferenceDetailsWithAllTranslations(@PathVariable String codeList) {
        ReferenceDTO dto = referenceService.getReferenceDetailsWithAllTranslations(codeList);
        return ResponseEntity.ok(dto);
    }

//    private final ReferenceService referenceService;
//    private final ReferenceMapper referenceMapper;
//
//    @GetMapping("/all")
//    public ResponseEntity<List<ReferenceDTO>> getAllReferences(
//            @RequestParam(required = false, defaultValue = "false") boolean includeTranslations,
//            @RequestParam(required = false) String lang) {
//        List<CodeList> codeLists = referenceService.getAllReferences();
//        if (includeTranslations && lang != null) {
//            return ResponseEntity.ok(referenceMapper.toDTOListWithLang(codeLists, lang));
//        }
//        return ResponseEntity.ok(referenceMapper.toDTOList(codeLists));
//    }
//
//    @GetMapping("/all-with-translations")
//    public ResponseEntity<List<ReferenceDTO>> getAllReferencesWithTranslations() {
//        List<CodeList> codeLists = referenceService.getAllReferences();
//        return ResponseEntity.ok(referenceMapper.toDTOListWithAllTranslations(codeLists));
//    }


}