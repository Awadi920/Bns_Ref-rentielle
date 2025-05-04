package com.bns.bnsref.Mappers;

import com.bns.bnsref.dto.RefDataSpecValueTranslationDTO;
import com.bns.bnsref.Entity.Ref_DataSpecValueTranslation;
import org.springframework.stereotype.Component;

@Component
public class RefDataSpecValueTranslationMapper {

    public RefDataSpecValueTranslationDTO toDTO(Ref_DataSpecValueTranslation translation) {
        if (translation == null) return null;

        RefDataSpecValueTranslationDTO dto = new RefDataSpecValueTranslationDTO();
        dto.setCodeRefDataSpecValueTranslation(translation.getCodeRefDataSpecValueTranslation());
        dto.setCodeRefDataSpecValue(translation.getRefDataSpecValue() != null ? translation.getRefDataSpecValue().getCodeRefDataSpecValue() : null);
        dto.setLanguageCode(translation.getLanguage() != null ? translation.getLanguage().getCodeLanguage() : null);
        dto.setValue(translation.getValue());
        return dto;
    }

    public Ref_DataSpecValueTranslation toEntity(RefDataSpecValueTranslationDTO dto) {
        if (dto == null) return null;

        Ref_DataSpecValueTranslation translation = new Ref_DataSpecValueTranslation();
        translation.setCodeRefDataSpecValueTranslation(dto.getCodeRefDataSpecValueTranslation());
        translation.setValue(dto.getValue());
        // Les relations (refDataSpecValue et language) seront gérées dans le service
        return translation;
    }
}
