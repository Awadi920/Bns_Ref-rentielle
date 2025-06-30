package com.bns.bnsref.Mappers;


import com.bns.bnsref.Entity.Language;
import com.bns.bnsref.dao.LanguageDAO;
import com.bns.bnsref.dto.RefDataSpecValueTranslationDTO;
import com.bns.bnsref.dto.Ref_DataSpecValueDTO;
import com.bns.bnsref.Entity.Ref_DataSpecValueTranslation;
import com.bns.bnsref.Entity.Ref_DataSpecValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class Ref_DataSpecValueMapper {
    private final LanguageDAO languageDAO;

    public Ref_DataSpecValueDTO toDTO(Ref_DataSpecValue entity) {
        if (entity == null) return null;

        return Ref_DataSpecValueDTO.builder()
                .codeRefDataSpecValue(entity.getCodeRefDataSpecValue())
                .value(entity.getValue())
                .codeRefDataSpec(entity.getRefDataSpec() != null ? entity.getRefDataSpec().getCodeRefDataSpec() : null)
                .refDataValueCode(entity.getRefDataValue() != null ? entity.getRefDataValue().getCodeRefDataValue() : null)
                .languageCode(entity.getLanguage() != null ? entity.getLanguage().getCodeLanguage() : null) // Ajout
                .rowId(entity.getRowId())
                .build();
    }

    public Ref_DataSpecValueDTO toDTOWithAllTranslations(Ref_DataSpecValue entity) {
        if (entity == null) return null;

        Ref_DataSpecValueDTO dto = toDTO(entity);
        dto.setTranslations(entity.getTranslations().stream()
                .map(t -> new RefDataSpecValueTranslationDTO(
                        t.getCodeRefDataSpecValueTranslation(),
                        t.getRefDataSpecValue().getCodeRefDataSpecValue(),
                        t.getLanguage().getCodeLanguage(),
                        t.getValue()))
                .collect(Collectors.toList()));
        return dto;
    }

    public Ref_DataSpecValueDTO toDTOWithLang(Ref_DataSpecValue entity, String lang) {
        if (entity == null) return null;

        Ref_DataSpecValueDTO dto = new Ref_DataSpecValueDTO();
        dto.setCodeRefDataSpecValue(entity.getCodeRefDataSpecValue());
        dto.setCodeRefDataSpec(entity.getRefDataSpec() != null ? entity.getRefDataSpec().getCodeRefDataSpec() : null);
        dto.setRefDataValueCode(entity.getRefDataValue() != null ? entity.getRefDataValue().getCodeRefDataValue() : null);
        dto.setLanguageCode(entity.getLanguage() != null ? entity.getLanguage().getCodeLanguage() : null); // Ajout
        dto.setRowId(entity.getRowId());

        if (lang != null) {
            Ref_DataSpecValueTranslation translation = entity.getTranslations().stream()
                    .filter(t -> t.getLanguage().getCodeLanguage().equalsIgnoreCase(lang))
                    .findFirst()
                    .orElse(null);
            if (translation != null) {
                dto.setValue(translation.getValue());
                dto.setTranslations(List.of(new RefDataSpecValueTranslationDTO(
                        translation.getCodeRefDataSpecValueTranslation(),
                        translation.getRefDataSpecValue().getCodeRefDataSpecValue(),
                        translation.getLanguage().getCodeLanguage(),
                        translation.getValue())));
            } else {
                dto.setValue(null);
                dto.setTranslations(Collections.emptyList());
            }
        } else {
            dto.setValue(entity.getValue());
            dto.setTranslations(Collections.emptyList());
        }

        return dto;
    }

    public Ref_DataSpecValue toEntity(Ref_DataSpecValueDTO dto) {
        if (dto == null) return null;

        Language language = null;
        if (dto.getLanguageCode() != null) {
            language = languageDAO.findById(dto.getLanguageCode())
                    .orElseThrow(() -> new RuntimeException("Langue non trouvée: " + dto.getLanguageCode()));
        }

        return Ref_DataSpecValue.builder()
                .codeRefDataSpecValue(dto.getCodeRefDataSpecValue())
                .value(dto.getValue())
                .rowId(dto.getRowId())
                .language(language)
                .build();
    }
}