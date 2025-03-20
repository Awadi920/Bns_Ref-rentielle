package com.bns.bnsref.Mappers;


import com.bns.bnsref.DTO.Ref_DataSpecDTO;
import com.bns.bnsref.Entity.Ref_DataSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

// Ref_DataSpecMapper.java
@Component
@RequiredArgsConstructor
public class Ref_DataSpecMapper {

    private final Ref_DataSpecValueMapper refDataSpecValueMapper;

    public Ref_DataSpecDTO toDTO(Ref_DataSpec entity) {
        return Ref_DataSpecDTO.builder()
                .codeRefDataSpec(entity.getCodeRefDataSpec())
                .designation(entity.getDesignation())
                .description(entity.getDescription())
                .codeListCode(entity.getCodeList().getCodeList())
                .specValues(entity.getRefDataSpecValues().stream() // <-- Conversion des valeurs
                        .map(refDataSpecValueMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public Ref_DataSpec toEntity(Ref_DataSpecDTO dto) {
        return Ref_DataSpec.builder()
                .designation(dto.getDesignation())
                .description(dto.getDescription())
                .build();
    }
}

