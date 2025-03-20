package com.bns.bnsref.Mappers;

import com.bns.bnsref.DTO.Ref_DataDTO;

import com.bns.bnsref.Entity.Ref_Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class Ref_DataMapper {

    private final Ref_DataValueMapper refDataValueMapper;

    // Injection du mapper pour les valeurs
    public Ref_DataMapper(Ref_DataValueMapper refDataValueMapper) {
        this.refDataValueMapper = refDataValueMapper;
    }

//    // Convertir Entity → DTO
//    public Ref_DataDTO toDTO(Ref_Data entity) {
//        return Ref_DataDTO.builder()
//                .designation(entity.getDesignation())
//                .description(entity.getDescription())
//                .codeListCode(entity.getCodeList().getCodeList())
//                .values(entity.getRefDataValues().stream()
//                        .map(refDataValueMapper::toDTO)
//                        .collect(Collectors.toList()))
//                .build();
//    }
//
//    // Convertir DTO → Entity (méthode manquante)
//    public Ref_Data toEntity(Ref_DataDTO dto) {
//        return Ref_Data.builder()
//                .codeRefData(dto.getCodeRefData())
//                .designation(dto.getDesignation())
//                .description(dto.getDescription())
//                // Note: codeList doit être associé manuellement dans le service
//                .build();
//    }

    // Convertir DTO → Entity
    public Ref_Data toEntity(Ref_DataDTO dto) {
        return Ref_Data.builder()
                .designation(dto.getDesignation())
                .description(dto.getDescription())
                // Note: codeList sera associé dans le service
                .build();
    }

    // Convertir Entity → DTO
    public Ref_DataDTO toDTO(Ref_Data entity) {
        return Ref_DataDTO.builder()
                .codeRefData(entity.getCodeRefData())
                .designation(entity.getDesignation())
                .description(entity.getDescription())
                .codeListCode(entity.getCodeList() != null ? entity.getCodeList().getCodeList() : null)
                .values(entity.getRefDataValues() != null ? entity.getRefDataValues().stream()
                        .map(refDataValueMapper::toDTO)
                        .collect(Collectors.toList()) : null)
                .build();
    }
}