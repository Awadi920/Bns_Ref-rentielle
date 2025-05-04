package com.bns.bnsref.Controller;


import com.bns.bnsref.dto.Ref_DataValueDTO;
import com.bns.bnsref.Service.Ref_DataValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/refdatavalue")
@RequiredArgsConstructor
public class Ref_DataValueController {

    private final Ref_DataValueService refDataValueService;

    @PostMapping("/add")
    public ResponseEntity<Ref_DataValueDTO> addRefDataValue(@RequestBody Ref_DataValueDTO refDataValueDTO) {
        Ref_DataValueDTO createdRefDataValue = refDataValueService.addRefDataValue(refDataValueDTO);
        return ResponseEntity.status(201).body(createdRefDataValue);
    }

    @PutMapping("/update/{codeRefDataValue}")
    public ResponseEntity<Ref_DataValueDTO> updateRefDataValue(@PathVariable String codeRefDataValue, @RequestBody Ref_DataValueDTO refDataValueDTO) {
        Ref_DataValueDTO updatedRefDataValue = refDataValueService.updateRefDataValue(codeRefDataValue, refDataValueDTO);
        return ResponseEntity.ok(updatedRefDataValue);
    }

//    @DeleteMapping("/delete/{codeRefDataValue}")
//    public ResponseEntity<Void> deleteRefDataValue(@PathVariable String codeRefDataValue) {
//        refDataValueService.deleteRefDataValue(codeRefDataValue);
//        return ResponseEntity.noContent().build();
//    }

    @DeleteMapping("/delete/{codeRefDataValue}")
    public ResponseEntity<String> deleteRefDataValue(@PathVariable String codeRefDataValue) {
        refDataValueService.deleteRefDataValue(codeRefDataValue);
        return ResponseEntity.ok("Ref_DataValue and associated children deleted successfully!");
    }

    @GetMapping("/{codeRefDataValue}")
    public ResponseEntity<Ref_DataValueDTO> getRefDataValueById(@PathVariable String codeRefDataValue) {
        Ref_DataValueDTO refDataValueDTO = refDataValueService.getRefDataValueById(codeRefDataValue);
        return ResponseEntity.ok(refDataValueDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ref_DataValueDTO>> getAllRefDataValues() {
        List<Ref_DataValueDTO> refDataValueList = refDataValueService.getAllRefDataValues();
        return ResponseEntity.ok(refDataValueList);
    }

    @PostMapping("/assign-relation")
    public ResponseEntity<Ref_DataValueDTO> assignRelation(@RequestBody Ref_DataValueDTO dto) {
        Ref_DataValueDTO updated = refDataValueService.assignRelation(dto);
        return ResponseEntity.ok(updated);
    }
}
