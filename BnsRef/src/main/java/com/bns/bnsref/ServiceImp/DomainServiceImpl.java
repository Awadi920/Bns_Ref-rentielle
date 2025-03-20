package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.DAO.DomainDAO;
import com.bns.bnsref.DTO.DomainDTO;
import com.bns.bnsref.Entity.Domain;
import com.bns.bnsref.Mappers.DomainMapper;
import com.bns.bnsref.Service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DomainServiceImpl implements DomainService {

    private final DomainDAO domainDAO;
    private final DomainMapper domainMapper;

    @Override
    public DomainDTO addDomain(DomainDTO domainDTO) {
        // Générer automatiquement le codeDomain
        String lastCodeDomain = domainDAO.findLastDomainCode().orElse("DOM000");
        int nextId = Integer.parseInt(lastCodeDomain.replace("DOM", "")) + 1;
        String newCodeDomain = String.format("DOM%03d", nextId); // Format DOM001, DOM002...

        // Convertir le DTO en entité
        Domain domain = domainMapper.toEntity(domainDTO);
        domain.setCodeDomain(newCodeDomain);

        // Sauvegarder l'entité
        Domain savedDomain = domainDAO.save(domain);

        // Retourner le DTO
        return domainMapper.toDTO(savedDomain);

    }

    @Override
    public DomainDTO updateDomain(String codeDomain, DomainDTO domainDTO) {
        Domain domain = domainDAO.findById(codeDomain)
                .orElseThrow(() -> new RuntimeException("Domain not found"));
        domain.setDomainName(domainDTO.getDomainName());
        domain.setDomainDescription(domainDTO.getDomainDescription());
        return domainMapper.toDTO(domainDAO.save(domain));
    }

    @Override
    public void deleteDomain(String codeDomain) {
        domainDAO.deleteById(codeDomain);
    }

    @Override
    public DomainDTO getDomainById(String codeDomain) {
        return domainMapper.toDTO(domainDAO.findById(codeDomain)
                .orElseThrow(() -> new RuntimeException("Domain not found")));
    }

    @Override
    public List<DomainDTO> getAllDomains() {
        return domainDAO.findAll().stream().map(domainMapper::toDTO).collect(Collectors.toList());
    }
}

