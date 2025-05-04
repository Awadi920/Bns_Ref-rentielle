package com.bns.bnsref.Controller;

import com.bns.bnsref.dao.*;
import com.bns.bnsref.dto.CodeListDTO;
import com.bns.bnsref.Service.CodeListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/codelists")
@RequiredArgsConstructor
//@CrossOrigin("*")
public class CodeListController {

    @Autowired
    private final CodeListService codeListService;


    @PostMapping("/add")
    public ResponseEntity<CodeListDTO> addCodeList(@RequestBody CodeListDTO codeListDTO) {
        return ResponseEntity.ok(codeListService.addCodeList(codeListDTO));
    }

    @PutMapping("/update/{codeListId}")
    public ResponseEntity<CodeListDTO> updateCodeList(@PathVariable String codeListId, @RequestBody CodeListDTO codeListDTO) {
        return ResponseEntity.ok(codeListService.updateCodeList(codeListId, codeListDTO));
    }

    @DeleteMapping("/delete/{codeListId}")
    public ResponseEntity<String> deleteCodeList(@PathVariable String codeListId) {
        codeListService.deleteCodeList(codeListId);
        return ResponseEntity.ok("CodeList and associated entities deleted successfully!");
    }

    @GetMapping("/{codeListId}")
    public ResponseEntity<CodeListDTO> getCodeListById(@PathVariable String codeListId) {
        return ResponseEntity.ok(codeListService.getCodeListById(codeListId));
    }

    @GetMapping("/all")
   // @PreAuthorize("hasAuthority('USER')")
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CodeListDTO>> getAllCodeLists() {
        return ResponseEntity.ok(codeListService.getAllCodeLists());
    }

    @GetMapping("/filtered")
    public ResponseEntity<List<CodeListDTO>> getFilteredAndSortedCodeLists() {
        return ResponseEntity.ok(codeListService.getFilteredAndSortedCodeLists());
    }


}
