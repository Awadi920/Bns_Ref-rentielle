package com.bns.bnsref.Mappers;

import com.bns.bnsref.dto.RefDataTranslationDTO;
import com.bns.bnsref.Entity.Ref_DataTranslation;
import org.springframework.stereotype.Component;

@Component
public class RefDataTranslationMapper {

    public RefDataTranslationDTO toDTO(Ref_DataTranslation translation) {
        if (translation == null) return null;

        RefDataTranslationDTO dto = new RefDataTranslationDTO();
        dto.setCodeRefDataTranslation(translation.getCodeRefDataTranslation());
        dto.setCodeRefData(translation.getRefData() != null ? translation.getRefData().getCodeRefData() : null);
        dto.setLanguageCode(translation.getLanguage() != null ? translation.getLanguage().getCodeLanguage() : null);
        dto.setDesignation(translation.getDesignation());
        dto.setDescription(translation.getDescription());
        return dto;
    }

    public Ref_DataTranslation toEntity(RefDataTranslationDTO dto) {
        if (dto == null) return null;

        Ref_DataTranslation translation = new Ref_DataTranslation();
        translation.setCodeRefDataTranslation(dto.getCodeRefDataTranslation());
        translation.setDesignation(dto.getDesignation());
        translation.setDescription(dto.getDescription());
        // Les relations (refData et language) seront gérées dans le service
        return translation;
    }
}
