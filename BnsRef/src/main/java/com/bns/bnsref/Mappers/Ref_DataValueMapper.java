package com.bns.bnsref.Mappers;

import com.bns.bnsref.dto.RefDataValueTranslationDTO;
import com.bns.bnsref.dto.Ref_DataValueDTO;
import com.bns.bnsref.Entity.Ref_DataValueTranslation;
import com.bns.bnsref.Entity.Ref_DataValue;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class Ref_DataValueMapper {

    public Ref_DataValueDTO toDTO(Ref_DataValue refDataValue) {
        if (refDataValue == null) return null;

        Ref_DataValueDTO dto = new Ref_DataValueDTO();
        dto.setCodeRefDataValue(refDataValue.getCodeRefDataValue());
        dto.setValue(refDataValue.getValue());
        dto.setCodeRefData(refDataValue.getRefData() != null ? refDataValue.getRefData().getCodeRefData() : null);
        dto.setParentValueCode(refDataValue.getParentValue() != null ? refDataValue.getParentValue().getCodeRefDataValue() : null);

        dto.setChildValues(refDataValue.getChildValues().stream()
                .map(this::toDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    public Ref_DataValue toEntity(Ref_DataValueDTO dto) {
        if (dto == null) return null;

        Ref_DataValue entity = new Ref_DataValue();
        entity.setCodeRefDataValue(dto.getCodeRefDataValue());
        entity.setValue(dto.getValue());
        // Les relations (refData, parentValue, childValues) sont gérées dans le service
        return entity;
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
        dto.setChildValues(entity.getChildValues().stream()
                .map(this::toDTOWithAllTranslations)
                .collect(Collectors.toList()));
        return dto;
    }

    public Ref_DataValueDTO toDTOWithLang(Ref_DataValue entity, String lang) {
        if (entity == null) return null;
        Ref_DataValueDTO dto = new Ref_DataValueDTO();
        dto.setCodeRefDataValue(entity.getCodeRefDataValue());
        dto.setCodeRefData(entity.getRefData().getCodeRefData());
        dto.setParentValueCode(entity.getParentValue() != null ? entity.getParentValue().getCodeRefDataValue() : null);

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

        dto.setChildValues(entity.getChildValues().stream()
                .map(v -> toDTOWithLang(v, lang))
                .collect(Collectors.toList()));

        return dto;
    }
}