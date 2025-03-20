package com.bns.bnsref.Service;

import com.bns.bnsref.DTO.ProducerDTO;

import java.util.*;

public interface ProducerService {
    ProducerDTO addProducer(ProducerDTO producerDTO);
    ProducerDTO updateProducer(String codeProd, ProducerDTO producerDTO);
    void deleteProducer(String codeProd);
    ProducerDTO getProducerById(String codeProd);
    List<ProducerDTO> getAllProducers();
}
