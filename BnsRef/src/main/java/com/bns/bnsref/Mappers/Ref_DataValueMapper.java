package com.bns.bnsref.Mappers;

import com.bns.bnsref.DTO.Ref_DataValueDTO;
import com.bns.bnsref.Entity.Ref_DataValue;
import org.springframework.stereotype.Component;

@Component
public class Ref_DataValueMapper {

    // Méthode existante
    public Ref_DataValueDTO toDTO(Ref_DataValue entity) {
        return Ref_DataValueDTO.builder()
                .codeRefDataValue(entity.getCodeRefDataValue())
                .value(entity.getValue())
                .codeRefData(entity.getRefData().getCodeRefData())
                .codeLanguage(entity.getLanguage().getCodeLanguage())
                .build();
    }

    // Nouvelle méthode pour convertir DTO → Entity
    public Ref_DataValue toEntity(Ref_DataValueDTO dto) {
        return Ref_DataValue.builder()
                .value(dto.getValue())
                // Note : Les associations (refData, language) doivent être gérées dans le service
                .build();
    }
}