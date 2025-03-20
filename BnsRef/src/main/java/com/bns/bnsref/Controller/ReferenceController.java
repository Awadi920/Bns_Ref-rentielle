package com.bns.bnsref.Controller;

import com.bns.bnsref.DTO.ReferenceDTO;
import com.bns.bnsref.Service.ReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/references")
public class ReferenceController {

    @Autowired
    private ReferenceService referenceService;

    @GetMapping("all")
    //@JsonView(Views.Detailed.class) // Inclut 'values' et 'specValues'
    public ResponseEntity<List<ReferenceDTO>> getAllReferences() {
        List<ReferenceDTO> references = referenceService.getAllReferences();
        return ResponseEntity.ok(references);
    }

    @GetMapping("/{idOrLabel}")
    public ResponseEntity<ReferenceDTO> getReferenceByIdOrLabel(@PathVariable String idOrLabel) {
        return referenceService.getReferenceByIdOrLabel(idOrLabel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

