package com.bns.bnsref.Controller;

import com.bns.bnsref.dto.MetaDataDTO;
import com.bns.bnsref.Service.MetaDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metadata")
@RequiredArgsConstructor
public class MetaDataController {

    private final MetaDataService metaDataService;

    @PostMapping("/add")
    public ResponseEntity<MetaDataDTO> addMetaData(@RequestBody MetaDataDTO metaDataDTO) {
        MetaDataDTO createdMetaData = metaDataService.addMetaData(metaDataDTO);
        return ResponseEntity.status(201).body(createdMetaData);
    }

    @PutMapping("/update/{codeMetaData}")
    public ResponseEntity<MetaDataDTO> updateMetaData(@PathVariable String codeMetaData, @RequestBody MetaDataDTO metaDataDTO) {
        MetaDataDTO updatedMetaData = metaDataService.updateMetaData(codeMetaData, metaDataDTO);
        return ResponseEntity.ok(updatedMetaData);
    }

    @DeleteMapping("/delete/{codeMetaData}")
    public ResponseEntity<Void> deleteMetaData(@PathVariable String codeMetaData) {
        metaDataService.deleteMetaData(codeMetaData);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{codeMetaData}")
    public ResponseEntity<MetaDataDTO> getMetaDataById(@PathVariable String codeMetaData) {
        MetaDataDTO metaDataDTO = metaDataService.getMetaDataById(codeMetaData);
        return ResponseEntity.ok(metaDataDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MetaDataDTO>> getAllMetaData() {
        List<MetaDataDTO> metaDataList = metaDataService.getAllMetaData();
        return ResponseEntity.ok(metaDataList);
    }
}