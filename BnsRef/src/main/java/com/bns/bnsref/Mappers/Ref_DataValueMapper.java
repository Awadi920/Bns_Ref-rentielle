package com.bns.bnsref.Mappers;

import com.bns.bnsref.dto.RefDataValueTranslationDTO;
import com.bns.bnsref.dto.Ref_DataValueDTO;
import com.bns.bnsref.Entity.Ref_DataValueTranslation;
import com.bns.bnsref.Entity.Ref_DataValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class Ref_DataValueMapper {

    public Ref_DataValueDTO toDTO(Ref_DataValue entity) {
        if (entity == null) return null;

        Ref_DataValueDTO dto = Ref_DataValueDTO.builder()
                .codeRefDataValue(entity.getCodeRefDataValue())
                .value(entity.getValue())
                .codeRefData(entity.getRefData() != null ? entity.getRefData().getCodeRefData() : null)
                .build();

        return dto;
    }

    public Ref_DataValueDTO toDTOWithAllTranslations(Ref_DataValue entity) {
        if (entity == null) return null;

        Ref_DataValueDTO dto = toDTO(entity);
        dto.setTranslations(entity.getTranslations().stream()
                .map(t -> new RefDataValueTranslationDTO(
                        t.getCodeRefDataValueTranslation(),
                        t.getRefDataValue().getCodeRefDataValue(),
                        t.getLanguage().getCodeLanguage(),
                        t.getValue()))
                .collect(Collectors.toList()));
        return dto;
    }

    public Ref_DataValueDTO toDTOWithLang(Ref_DataValue entity, String lang) {
        if (entity == null) return null;

        Ref_DataValueDTO dto = new Ref_DataValueDTO();
        dto.setCodeRefDataValue(entity.getCodeRefDataValue());
        dto.setCodeRefData(entity.getRefData() != null ? entity.getRefData().getCodeRefData() : null);

        if (lang != null) {
            Ref_DataValueTranslation translation = entity.getTranslations().stream()
                    .filter(t -> t.getLanguage().getCodeLanguage().equalsIgnoreCase(lang))
                    .findFirst()
                    .orElse(null);
            if (translation != null) {
                dto.setValue(translation.getValue());
                dto.setTranslations(List.of(new RefDataValueTranslationDTO(
                        translation.getCodeRefDataValueTranslation(),
                        translation.getRefDataValue().getCodeRefDataValue(),
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

    public Ref_DataValue toEntity(Ref_DataValueDTO dto) {
        return Ref_DataValue.builder()
                .codeRefDataValue(dto.getCodeRefDataValue())
                .value(dto.getValue())
                .build();
    }
}