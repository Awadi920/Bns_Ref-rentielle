package com.bns.bnsref.Controller;


import com.bns.bnsref.DTO.Ref_DataDTO;
import com.bns.bnsref.Service.Ref_DataService;
import com.bns.bnsref.Views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/refdata")
@RequiredArgsConstructor
public class Ref_DataController {

    private final Ref_DataService refDataService;

    @PostMapping("/add")
    public ResponseEntity<Ref_DataDTO> addRefData(@RequestBody Ref_DataDTO refDataDTO) {
        Ref_DataDTO createdRefData = refDataService.addRefData(refDataDTO);
        return ResponseEntity.status(201).body(createdRefData);
    }

    @PutMapping("/update/{codeRefData}")
    public ResponseEntity<Ref_DataDTO> updateRefData(@PathVariable String codeRefData, @RequestBody Ref_DataDTO refDataDTO) {
        Ref_DataDTO updatedRefData = refDataService.updateRefData(codeRefData, refDataDTO);
        return ResponseEntity.ok(updatedRefData);
    }

    @DeleteMapping("/delete/{codeRefData}")
    public ResponseEntity<Void> deleteRefData(@PathVariable String codeRefData) {
        refDataService.deleteRefData(codeRefData);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{codeRefData}")
    public ResponseEntity<Ref_DataDTO> getRefDataById(@PathVariable String codeRefData) {
        Ref_DataDTO refDataDTO = refDataService.getRefDataById(codeRefData);
        return ResponseEntity.ok(refDataDTO);
    }

    @GetMapping("/all")
    @JsonView(Views.Basic.class) // Exclut 'values'
    public List<Ref_DataDTO> getAllRefData() {
        return refDataService.getAllRefData();
    }
}
