package com.bns.bnsref.Controller;

import com.bns.bnsref.Filter.Filter;
import com.bns.bnsref.Filter.SortCriteria;
import com.bns.bnsref.Service.FilterService;
import com.bns.bnsref.dto.EntityCriteriaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filters")
@RequiredArgsConstructor
public class FilterController {

    private final FilterService filterService;

    @PostMapping
    public ResponseEntity<Filter> createFilter(@RequestBody Filter filter) {
        Filter createdFilter = filterService.createFilter(filter);
        return ResponseEntity.status(201).body(createdFilter);
    }

    @DeleteMapping("/{filterId}")
    public ResponseEntity<Void> deleteFilter(@PathVariable Long filterId) {
        filterService.deleteFilter(filterId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sort")
    public ResponseEntity<SortCriteria> createSortCriteria(@RequestBody SortCriteria sortCriteria) {
        SortCriteria createdSortCriteria = filterService.createSortCriteria(sortCriteria);
        return ResponseEntity.status(201).body(createdSortCriteria);
    }

    @DeleteMapping("/sort/{sortCriteriaId}")
    public ResponseEntity<Void> deleteSortCriteria(@PathVariable Long sortCriteriaId) {
        filterService.deleteSortCriteria(sortCriteriaId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearAllCriteria(@RequestParam String entityName) {
        filterService.clearAllCriteria(entityName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/criteria")
    public EntityCriteriaDTO getAllCriteria(@RequestParam String entityName) {
        return filterService.getAllCriteria(entityName);
    }
}
