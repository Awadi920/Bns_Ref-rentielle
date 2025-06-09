package com.bns.bnsref.Controller;

import com.bns.bnsref.Entity.Ref_DataSpec;
import com.bns.bnsref.Mappers.Ref_DataSpecMapper;
import com.bns.bnsref.dao.Ref_DataSpecDAO;
import com.bns.bnsref.dto.ColumnOrderDTO;
import com.bns.bnsref.dto.Ref_DataSpecDTO;
import com.bns.bnsref.dto.ReferenceDTO;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Mappers.ReferenceMapper;
import com.bns.bnsref.Service.ReferenceService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/references")
@RequiredArgsConstructor
@Slf4j
public class ReferenceController {


    private final ReferenceService referenceService;
    private final ReferenceMapper referenceMapper;
    private final Ref_DataSpecDAO refDataSpecDAO;
    private final Ref_DataSpecMapper refDataSpecMapper;


    private static final Logger logger = LoggerFactory.getLogger(ReferenceController.class);


    @GetMapping("/all")
    public ResponseEntity<List<ReferenceDTO>> getAllReferences() {
        List<ReferenceDTO> references = referenceService.getAllReferencesAsDTO();
        log.info("Returning {} references", references.size());
        return ResponseEntity.ok(references);
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

    @PutMapping("/{codeList}/columns/order")
    public ResponseEntity<Void> updateColumnOrder(
            @PathVariable String codeList,
            @RequestBody List<ColumnOrderDTO> columnOrders) {
        referenceService.updateColumnOrder(codeList, columnOrders);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/test/refDataSpec/{codeList}")
    public ResponseEntity<List<Ref_DataSpecDTO>> getRefDataSpecByCodeList(@PathVariable String codeList) {
        List<Ref_DataSpec> refDataSpecs = refDataSpecDAO.findByCodeListCodeList(codeList);
        List<Ref_DataSpecDTO> dtos = refDataSpecs.stream()
                .map(refDataSpecMapper::toDTO)
                .collect(Collectors.toList());
        logger.info("Found {} Ref_DataSpec for CodeList {}: {}", dtos.size(), codeList, dtos);
        return ResponseEntity.ok(dtos);
    }
}