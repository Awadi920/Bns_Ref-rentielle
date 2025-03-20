package com.bns.bnsref.Controller;

import com.bns.bnsref.DAO.*;
import com.bns.bnsref.DTO.CodeListDTO;
import com.bns.bnsref.Service.CodeListService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/codelists")
@RequiredArgsConstructor
public class CodeListController {

    @Autowired
    private final CodeListService codeListService;
    @Autowired
    private final CodeListDAO codeListDAO;
    @Autowired
    private final DomainDAO domainDAO;
    @Autowired
    private final CategoryDAO categoryDAO;
    @Autowired
    private final ProducerDAO producerDAO;


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
    public ResponseEntity<List<CodeListDTO>> getAllCodeLists() {
        return ResponseEntity.ok(codeListService.getAllCodeLists());
    }

}
