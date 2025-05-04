package com.bns.bnsref.Mappers;

import com.bns.bnsref.dto.RefDataValueTranslationDTO;
import com.bns.bnsref.Entity.Ref_DataValueTranslation;
import org.springframework.stereotype.Component;

@Component
public class RefDataValueTranslationMapper {

    public RefDataValueTranslationDTO toDTO(Ref_DataValueTranslation translation) {
        if (translation == null) return null;

        RefDataValueTranslationDTO dto = new RefDataValueTranslationDTO();
        dto.setCodeRefDataValueTranslation(translation.getCodeRefDataValueTranslation());
        dto.setCodeRefDataValue(translation.getRefDataValue() != null ? translation.getRefDataValue().getCodeRefDataValue() : null);
        dto.setLanguageCode(translation.getLanguage() != null ? translation.getLanguage().getCodeLanguage() : null);
        dto.setValue(translation.getValue());
        return dto;
    }

    public Ref_DataValueTranslation toEntity(RefDataValueTranslationDTO dto) {
        if (dto == null) return null;

        Ref_DataValueTranslation translation = new Ref_DataValueTranslation();
        translation.setCodeRefDataValueTranslation(dto.getCodeRefDataValueTranslation());
        translation.setValue(dto.getValue());
        // Les relations (refDataValue et language) seront gérées dans le service
        return translation;
    }
}
