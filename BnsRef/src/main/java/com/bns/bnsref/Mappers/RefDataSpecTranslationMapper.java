package com.bns.bnsref.Mappers;

import com.bns.bnsref.dto.RefDataSpecTranslationDTO;
import com.bns.bnsref.Entity.Ref_DataSpecTranslation;
import org.springframework.stereotype.Component;

@Component
public class RefDataSpecTranslationMapper {

    public RefDataSpecTranslationDTO toDTO(Ref_DataSpecTranslation translation) {
        if (translation == null) return null;

        RefDataSpecTranslationDTO dto = new RefDataSpecTranslationDTO();
        dto.setCodeRefDataSpecTranslation(translation.getCodeRefDataSpecTranslation());
        dto.setCodeRefDataSpec(translation.getRefDataSpec() != null ? translation.getRefDataSpec().getCodeRefDataSpec() : null);
        dto.setLanguageCode(translation.getLanguage() != null ? translation.getLanguage().getCodeLanguage() : null);
        dto.setDesignation(translation.getDesignation());
        dto.setDescription(translation.getDescription());
        return dto;
    }

    public Ref_DataSpecTranslation toEntity(RefDataSpecTranslationDTO dto) {
        if (dto == null) return null;

        Ref_DataSpecTranslation translation = new Ref_DataSpecTranslation();
        translation.setCodeRefDataSpecTranslation(dto.getCodeRefDataSpecTranslation());
        translation.setDesignation(dto.getDesignation());
        translation.setDescription(dto.getDescription());
        // Les relations (refDataSpec et language) seront gérées dans le service
        return translation;
    }
}
