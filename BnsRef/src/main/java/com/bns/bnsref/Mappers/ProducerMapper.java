package com.bns.bnsref.Mappers;

import com.bns.bnsref.DTO.ProducerDTO;
import com.bns.bnsref.Entity.Producer;
import org.springframework.stereotype.Component;

@Component
public class ProducerMapper {

    public Producer toEntity(ProducerDTO dto) {
        return Producer.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .city(dto.getCity())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .website(dto.getWebsite())
                .build();
    }

    public ProducerDTO toDTO(Producer entity) {
        return ProducerDTO.builder()
                .codeProd(entity.getCodeProd())
                .name(entity.getName())
                .address(entity.getAddress())
                .city(entity.getCity())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .website(entity.getWebsite())
                .build();
    }
}

