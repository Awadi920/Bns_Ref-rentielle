package com.bns.bnsref.Controller;


import com.bns.bnsref.ServiceImp.CodeListServiceImpl;
import com.bns.bnsref.dto.Ref_DataDTO;
import com.bns.bnsref.Service.Ref_DataService;
import com.bns.bnsref.Views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/refdata")
@RequiredArgsConstructor
public class Ref_DataController {

    private final Ref_DataService refDataService;

//    @PostMapping("/add")
//    public ResponseEntity<Ref_DataDTO> addRefData(@RequestBody Ref_DataDTO refDataDTO) {
//        Ref_DataDTO createdRefData = refDataService.addRefData(refDataDTO);
//        return ResponseEntity.status(201).body(createdRefData);
//    }

    @PostMapping("/add")
    public ResponseEntity<Void> addRefData(@RequestBody Ref_DataDTO refDataDTO) {
        refDataService.addRefData(refDataDTO);
        return ResponseEntity.noContent().build();
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

//    @GetMapping("/all")
//    @PreAuthorize("hasRole('USER')")
//    @JsonView(Views.Basic.class) // Exclut 'values'
//    public List<Ref_DataDTO> getAllRefData() {
//        return refDataService.getAllRefData();
//    }


    @GetMapping("/all")
    public ResponseEntity<Page<Ref_DataDTO>> getAllRefData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(refDataService.getAllRefData(pageable));
    }

    @GetMapping("/filtered")
    @JsonView(Views.Basic.class)
    public List<Ref_DataDTO> getFilteredAndSortedRefData() {
        return refDataService.getFilteredAndSortedRefData();
    }
}

