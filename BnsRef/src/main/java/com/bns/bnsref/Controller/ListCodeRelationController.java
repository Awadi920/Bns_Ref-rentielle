package com.bns.bnsref.Controller;


import com.bns.bnsref.dto.ListCodeRelationDTO;
import com.bns.bnsref.Service.ListCodeRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/list-code-relations")
@RequiredArgsConstructor
public class ListCodeRelationController {

    private final ListCodeRelationService service;

    @PostMapping("/add")
    public ResponseEntity<ListCodeRelationDTO> create(@RequestBody ListCodeRelationDTO dto) {
        ListCodeRelationDTO created = service.createListCodeRelation(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ListCodeRelationDTO> update(@PathVariable Long id, @RequestBody ListCodeRelationDTO dto) {
        ListCodeRelationDTO updated = service.updateListCodeRelation(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListCodeRelationDTO> getById(@PathVariable Long id) {
        ListCodeRelationDTO relation = service.getListCodeRelationById(id);
        return ResponseEntity.ok(relation);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ListCodeRelationDTO>> getAll() {
        List<ListCodeRelationDTO> relations = service.getAllListCodeRelations();
        return ResponseEntity.ok(relations);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteListCodeRelation(id);
        return ResponseEntity.ok().build();
    }
}