package com.bns.bnsref.Controller;


import com.bns.bnsref.dto.Ref_DataSpecDTO;
import com.bns.bnsref.Service.Ref_DataSpecService;
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
@RequestMapping("/api/refdataspec")
@RequiredArgsConstructor
public class Ref_DataSpecController {

    private final Ref_DataSpecService refDataSpecService;

    @PostMapping("/add")
    public ResponseEntity<Ref_DataSpecDTO> addRefDataSpec(@RequestBody Ref_DataSpecDTO refDataSpecDTO) {
        Ref_DataSpecDTO createdRefDataSpec = refDataSpecService.addRefDataSpec(refDataSpecDTO);
        return ResponseEntity.status(201).body(createdRefDataSpec);
    }

    @PutMapping("/update/{codeRefDataSpec}")
    public ResponseEntity<Ref_DataSpecDTO> updateRefDataSpec(@PathVariable String codeRefDataSpec, @RequestBody Ref_DataSpecDTO refDataSpecDTO) {
        Ref_DataSpecDTO updatedRefDataSpec = refDataSpecService.updateRefDataSpec(codeRefDataSpec, refDataSpecDTO);
        return ResponseEntity.ok(updatedRefDataSpec);
    }

    @DeleteMapping("/delete/{codeRefDataSpec}")
    public ResponseEntity<Void> deleteRefDataSpec(@PathVariable String codeRefDataSpec) {
        refDataSpecService.deleteRefDataSpec(codeRefDataSpec);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{codeRefDataSpec}")
    public ResponseEntity<Ref_DataSpecDTO> getRefDataSpecById(@PathVariable String codeRefDataSpec) {
        Ref_DataSpecDTO refDataSpecDTO = refDataSpecService.getRefDataSpecById(codeRefDataSpec);
        return ResponseEntity.ok(refDataSpecDTO);
    }

//    @GetMapping("/all")
//    @JsonView(Views.Basic.class) // Exclut 'specValues'
//    public List<Ref_DataSpecDTO> getAllRefDataSpec() {
//        return refDataSpecService.getAllRefDataSpec();
//    }

    @GetMapping("/all")
    public ResponseEntity<Page<Ref_DataSpecDTO>> getAllRefDataSpec(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(refDataSpecService.getAllRefDataSpec(pageable));
    }


    @GetMapping("/filtered")
    @JsonView(Views.Basic.class)
    public ResponseEntity<List<Ref_DataSpecDTO>> getFilteredAndSortedRefDataSpec() {
        return ResponseEntity.ok(refDataSpecService.getFilteredAndSortedRefDataSpec());
    }


}
