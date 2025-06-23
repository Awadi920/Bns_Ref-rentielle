package com.bns.bnsref.Controller;


import com.bns.bnsref.dto.Ref_DataSpecValueDTO;
import com.bns.bnsref.Service.Ref_DataSpecValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refdataspecvalue")
@RequiredArgsConstructor
public class Ref_DataSpecValueController {

    private final Ref_DataSpecValueService refDataSpecValueService;

    @PostMapping("/add")
    public ResponseEntity<Ref_DataSpecValueDTO> addRefDataSpecValue(@RequestBody Ref_DataSpecValueDTO refDataSpecValueDTO) {
        Ref_DataSpecValueDTO createdRefDataSpecValue = refDataSpecValueService.addRefDataSpecValue(refDataSpecValueDTO);
        return ResponseEntity.status(201).body(createdRefDataSpecValue);
    }

    @PutMapping("/update/{codeRefDataSpecValue}")
    public ResponseEntity<Ref_DataSpecValueDTO> updateRefDataSpecValue(@PathVariable String codeRefDataSpecValue, @RequestBody Ref_DataSpecValueDTO refDataSpecValueDTO) {
        Ref_DataSpecValueDTO updatedRefDataSpecValue = refDataSpecValueService.updateRefDataSpecValue(codeRefDataSpecValue, refDataSpecValueDTO);
        return ResponseEntity.ok(updatedRefDataSpecValue);
    }

    @DeleteMapping("/delete/{codeRefDataSpecValue}")
    public ResponseEntity<Void> deleteRefDataSpecValue(@PathVariable String codeRefDataSpecValue) {
        refDataSpecValueService.deleteRefDataSpecValue(codeRefDataSpecValue);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{codeRefDataSpecValue}")
    public ResponseEntity<Ref_DataSpecValueDTO> getRefDataSpecValueById(@PathVariable String codeRefDataSpecValue) {
        Ref_DataSpecValueDTO refDataSpecValueDTO = refDataSpecValueService.getRefDataSpecValueById(codeRefDataSpecValue);
        return ResponseEntity.ok(refDataSpecValueDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ref_DataSpecValueDTO>> getAllRefDataSpecValues() {
        List<Ref_DataSpecValueDTO> refDataSpecValueList = refDataSpecValueService.getAllRefDataSpecValues();
        return ResponseEntity.ok(refDataSpecValueList);
    }
}
