package com.bns.bnsref.Controller;

import com.bns.bnsref.dao.*;
import com.bns.bnsref.dto.CodeListDTO;
import com.bns.bnsref.Service.CodeListService;
import com.bns.bnsref.dto.CodeListRowDTO;
import com.bns.bnsref.validation.UniqueLabelListValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;




@RestController
@RequestMapping("/api/codelists")
@RequiredArgsConstructor
//@CrossOrigin("*")
@Slf4j

public class CodeListController {

    @Autowired
    private final CodeListService codeListService;

    private final UniqueLabelListValidator uniqueLabelListValidator;



    public static class ErrorResponse {
        private String message;
        private Map<String, String> errors;

        public ErrorResponse(String message, Map<String, String> errors) {
            this.message = message;
            this.errors = errors;
        }

        public String getMessage() {
            return message;
        }

        public Map<String, String> getErrors() {
            return errors;
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCodeList(@Valid @RequestBody CodeListDTO codeListDTO) {
        try {
            log.info("Attempting to add CodeList with labelList: {}", codeListDTO.getLabelList());
            CodeListDTO savedCodeList = codeListService.addCodeList(codeListDTO);
            return ResponseEntity.ok(savedCodeList);
        } catch (RuntimeException e) {
            log.error("Error adding CodeList: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error adding CodeList", Map.of("error", e.getMessage())));
        }
    }

    @PutMapping("/update/{codeListId}")
    public ResponseEntity<?> updateCodeList(@PathVariable String codeListId, @Valid @RequestBody CodeListDTO codeListDTO) {
        try {
            log.info("Attempting to update CodeList with ID: {}, labelList: {}", codeListId, codeListDTO.getLabelList());
            uniqueLabelListValidator.setCodeListId(codeListId);
            CodeListDTO updatedCodeList = codeListService.updateCodeList(codeListId, codeListDTO);
            return ResponseEntity.ok(updatedCodeList);
        } catch (RuntimeException e) {
            log.error("Error updating CodeList: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error updating CodeList", Map.of("error", e.getMessage())));
        }
    }


//    @PostMapping("/add")
//    public ResponseEntity<CodeListDTO> addCodeList(@RequestBody CodeListDTO codeListDTO) {
//        return ResponseEntity.ok(codeListService.addCodeList(codeListDTO));
//    }
//
//    @PutMapping("/update/{codeListId}")
//    public ResponseEntity<CodeListDTO> updateCodeList(@PathVariable String codeListId, @RequestBody CodeListDTO codeListDTO) {
//        return ResponseEntity.ok(codeListService.updateCodeList(codeListId, codeListDTO));
//    }



    @DeleteMapping("/delete/{codeListId}")
    public ResponseEntity<Void> deleteCodeList(@PathVariable String codeListId) {
        log.info("Request to delete CodeList: {}", codeListId);
        codeListService.deleteCodeList(codeListId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{codeListId}")
    public ResponseEntity<CodeListDTO> getCodeListById(@PathVariable String codeListId) {
        return ResponseEntity.ok(codeListService.getCodeListById(codeListId));
    }

//    @GetMapping("/all")
//   // @PreAuthorize("hasAuthority('USER')")
//   @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<List<CodeListDTO>> getAllCodeLists() {
//        return ResponseEntity.ok(codeListService.getAllCodeLists());
//    }

    @GetMapping("/all")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<CodeListDTO>> getAllCodeLists(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(codeListService.getAllCodeLists(pageable));
    }

    @GetMapping("/filtered")
    public ResponseEntity<List<CodeListDTO>> getFilteredAndSortedCodeLists() {
        return ResponseEntity.ok(codeListService.getFilteredAndSortedCodeLists());
    }



    // Nouvel endpoint pour ajouter une ligne
    @PostMapping("/{codeListId}/rows")
    public ResponseEntity<Void> addCodeListRow(@PathVariable String codeListId, @RequestBody CodeListRowDTO rowDTO) {
        rowDTO.setCodeListId(codeListId);
        codeListService.addCodeListRow(rowDTO);
        return ResponseEntity.ok().build();
    }

    // Nouvel endpoint pour récupérer les lignes
    @GetMapping("/{codeListId}/rows")
    public ResponseEntity<List<CodeListRowDTO>> getCodeListRows(@PathVariable String codeListId) {
        return ResponseEntity.ok(codeListService.getCodeListRows(codeListId));
    }

    @PostMapping("/{codeListId}/rows/languages")
    public ResponseEntity<String> addCodeListRowWithLanguages(
            @PathVariable String codeListId,
            @RequestBody @Valid CodeListRowDTO rowDTO) {
        try {
            codeListService.addCodeListRowWithLanguages(codeListId, rowDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Row created successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
