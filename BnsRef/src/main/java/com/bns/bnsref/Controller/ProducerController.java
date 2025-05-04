package com.bns.bnsref.Controller;


import com.bns.bnsref.dto.ProducerDTO;
import com.bns.bnsref.Service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producers")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @PostMapping("/add")
    public ResponseEntity<ProducerDTO> addProducer(@RequestBody ProducerDTO producerDTO) {
        ProducerDTO createdProducer = producerService.addProducer(producerDTO);
        return ResponseEntity.status(201).body(createdProducer);
    }

    @PutMapping("/update/{codeProd}")
    public ResponseEntity<ProducerDTO> updateProducer(@PathVariable String codeProd, @RequestBody ProducerDTO producerDTO) {
        ProducerDTO updatedProducer = producerService.updateProducer(codeProd, producerDTO);
        return ResponseEntity.ok(updatedProducer);
    }

    @DeleteMapping("/delete/{codeProd}")
    public ResponseEntity<Void> deleteProducer(@PathVariable String codeProd) {
        producerService.deleteProducer(codeProd);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{codeProd}")
    public ResponseEntity<ProducerDTO> getProducerById(@PathVariable String codeProd) {
        ProducerDTO producerDTO = producerService.getProducerById(codeProd);
        return ResponseEntity.ok(producerDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProducerDTO>> getAllProducers() {
        List<ProducerDTO> producerList = producerService.getAllProducers();
        return ResponseEntity.ok(producerList);
    }
}
