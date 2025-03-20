package com.bns.bnsref.Service;

import com.bns.bnsref.DTO.DomainDTO;

import java.util.*;

public interface DomainService {
    DomainDTO addDomain(DomainDTO domainDTO);
    DomainDTO updateDomain(String codeDomain, DomainDTO domainDTO);
    void deleteDomain(String codeDomain);
    DomainDTO getDomainById(String codeDomain);
    List<DomainDTO> getAllDomains();
}

