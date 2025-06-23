package com.bns.bnsref.Controller;

import com.bns.bnsref.dao.DomainDAO;
import com.bns.bnsref.dto.DomainDTO;
import com.bns.bnsref.Service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page; // Importation correcte
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/domains")
@RequiredArgsConstructor
public class DomainController {

    private final DomainService domainService;
    private final DomainDAO domainDAO;

    @PostMapping("/add")
    public ResponseEntity<DomainDTO> createDomain(@RequestBody DomainDTO domainDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(domainService.addDomain(domainDTO));
    }

    @PutMapping("/update/{codeDomain}")
    public ResponseEntity<DomainDTO> updateDomain(@PathVariable String codeDomain, @RequestBody DomainDTO domainDTO) {
        return ResponseEntity.ok(domainService.updateDomain(codeDomain, domainDTO));
    }

    @DeleteMapping("/delete/{codeDomain}")
    public ResponseEntity<Void> deleteDomain(@PathVariable String codeDomain) {
        domainService.deleteDomain(codeDomain);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{codeDomain}")
    public ResponseEntity<DomainDTO> getDomainById(@PathVariable String codeDomain) {
        return ResponseEntity.ok(domainService.getDomainById(codeDomain));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DomainDTO>> getAllDomains() {
        return ResponseEntity.ok(domainService.getAllDomains());
    }


    @GetMapping
    public ResponseEntity<Page<DomainDTO>> getDomainsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(domainService.getDomainsPaginated(page, size));
    }


}
