package com.bns.bnsref.Mappers;


import com.bns.bnsref.DTO.Ref_DataSpecValueDTO;
import com.bns.bnsref.Entity.Ref_DataSpecValue;
import org.springframework.stereotype.Component;

// Ref_DataSpecValueMapper.java
@Component
public class Ref_DataSpecValueMapper {

    public Ref_DataSpecValueDTO toDTO(Ref_DataSpecValue entity) {
        return Ref_DataSpecValueDTO.builder()
                .codeRefDataSpecValue(entity.getCodeRefDataSpecValue())
                .value(entity.getValue())
                .codeRefDataSpec(entity.getRefDataSpec().getCodeRefDataSpec())
                .build();
    }

    public Ref_DataSpecValue toEntity(Ref_DataSpecValueDTO dto) {
        return Ref_DataSpecValue.builder()
                .value(dto.getValue())
                .build();
    }
}