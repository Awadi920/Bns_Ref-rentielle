package com.bns.bnsref.Mappers;

import com.bns.bnsref.DTO.DomainDTO;
import com.bns.bnsref.Entity.Domain;
import org.springframework.stereotype.Component;

@Component
public class DomainMapper {

    public Domain toEntity(DomainDTO dto) {
        return Domain.builder()
                .domainName(dto.getDomainName())
                .domainDescription(dto.getDomainDescription())
                .build();
    }

    public DomainDTO toDTO(Domain entity) {
        return DomainDTO.builder()
                .codeDomain(entity.getCodeDomain())
                .domainName(entity.getDomainName())
                .domainDescription(entity.getDomainDescription())
                .build();
    }
}

