package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.DAO.ProducerDAO;
import com.bns.bnsref.DTO.ProducerDTO;
import com.bns.bnsref.Entity.Producer;
import com.bns.bnsref.Mappers.ProducerMapper;
import com.bns.bnsref.Service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final ProducerDAO producerDAO;
    private final ProducerMapper producerMapper;

    @Override
    public ProducerDTO addProducer(ProducerDTO producerDTO) {
        // Générer automatiquement le codeProd
        String lastCodeProd = producerDAO.findLastProducerCode().orElse("PROD000");
        int nextId = Integer.parseInt(lastCodeProd.replace("PROD", "")) + 1;
        String newCodeProd = String.format("PROD%03d", nextId); // Format PROD001, PROD002...

        // Convertir le DTO en entité
        Producer producer = producerMapper.toEntity(producerDTO);
        producer.setCodeProd(newCodeProd);

        // Sauvegarder l'entité
        Producer savedProducer = producerDAO.save(producer);

        // Retourner le DTO
        return producerMapper.toDTO(savedProducer);
    }

    @Override
    public ProducerDTO updateProducer(String codeProd, ProducerDTO producerDTO) {
        Producer producer = producerDAO.findById(codeProd)
                .orElseThrow(() -> new RuntimeException("Producer not found"));
        producer.setName(producerDTO.getName());
        producer.setAddress(producerDTO.getAddress());
        producer.setCity(producerDTO.getCity());
        producer.setPhone(producerDTO.getPhone());
        producer.setEmail(producerDTO.getEmail());
        producer.setWebsite(producerDTO.getWebsite());

        return producerMapper.toDTO(producerDAO.save(producer));
    }

    @Override
    public void deleteProducer(String codeProd) {
        producerDAO.deleteById(codeProd);
    }

    @Override
    public ProducerDTO getProducerById(String codeProd) {
        return producerMapper.toDTO(producerDAO.findById(codeProd)
                .orElseThrow(() -> new RuntimeException("Producer not found")));
    }

    @Override
    public List<ProducerDTO> getAllProducers() {
        return producerDAO.findAll().stream()
                .map(producerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
