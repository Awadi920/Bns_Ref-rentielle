package com.bns.bnsref.Mappers;

import com.bns.bnsref.dto.RefDataTranslationDTO;
import com.bns.bnsref.dto.Ref_DataDTO;

import com.bns.bnsref.Entity.Ref_DataTranslation;
import com.bns.bnsref.Entity.Ref_Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class Ref_DataMapper {

    private final Ref_DataValueMapper refDataValueMapper;

    public Ref_DataDTO toDTO(Ref_Data entity) {
        if (entity == null) return null;

        Ref_DataDTO dto = Ref_DataDTO.builder()
                .codeRefData(entity.getCodeRefData())
                .designation(entity.getDesignation())
                .description(entity.getDescription())
                .codeListCode(entity.getCodeList() != null ? entity.getCodeList().getCodeList() : null)
                .orderPosition(entity.getOrderPosition()) // Ajout
                .build();

        dto.setValues(entity.getRefDataValues().stream()
                .map(refDataValueMapper::toDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    // New method for Basic view (excludes values and translations)
    public Ref_DataDTO toBasicDTO(Ref_Data entity) {
        if (entity == null) return null;

        return Ref_DataDTO.builder()
                .codeRefData(entity.getCodeRefData())
                .designation(entity.getDesignation())
                .description(entity.getDescription())
                .codeListCode(entity.getCodeList() != null ? entity.getCodeList().getCodeList() : null)
                .orderPosition(entity.getOrderPosition()) // Ajout
                .values(new ArrayList<>()) // Empty list to match DTO structure
                .translations(new ArrayList<>()) // Empty list to match DTO structure
                .build();
    }

    public Ref_Data toEntity(Ref_DataDTO dto) {
        return Ref_Data.builder()
                .codeRefData(dto.getCodeRefData())
                .designation(dto.getDesignation())
                .description(dto.getDescription())
                .orderPosition(dto.getOrderPosition()) // Ajout
                .build();
    }

    public Ref_DataDTO toDTOWithAllTranslations(Ref_Data entity) {
        if (entity == null) return null;

        Ref_DataDTO dto = toDTO(entity);
        dto.setTranslations(entity.getTranslations().stream()
                .map(t -> new RefDataTranslationDTO(
                        t.getCodeRefDataTranslation(),
                        t.getRefData().getCodeRefData(),
                        t.getLanguage().getCodeLanguage(),
                        t.getDesignation(),
                        t.getDescription()))
                .collect(Collectors.toList()));
        dto.setValues(entity.getRefDataValues().stream()
                .map(refDataValueMapper::toDTOWithAllTranslations)
                .collect(Collectors.toList()));
        return dto;
    }

    public Ref_DataDTO toDTOWithLang(Ref_Data entity, String lang) {
        if (entity == null) return null;

        Ref_DataDTO dto = new Ref_DataDTO(); // Ne pas appeler toDTO pour éviter les valeurs par défaut
        dto.setCodeRefData(entity.getCodeRefData());
        dto.setCodeListCode(entity.getCodeList() != null ? entity.getCodeList().getCodeList() : null);

        if (lang != null) {
            Ref_DataTranslation translation = entity.getTranslations().stream()
                    .filter(t -> t.getLanguage().getCodeLanguage().equalsIgnoreCase(lang))
                    .findFirst()
                    .orElse(null);
            if (translation != null) {
                dto.setDesignation(translation.getDesignation());
                dto.setDescription(translation.getDescription());
                // Remplir translations avec la traduction trouvée
                dto.setTranslations(List.of(new RefDataTranslationDTO(
                        translation.getCodeRefDataTranslation(),
                        translation.getRefData().getCodeRefData(),
                        translation.getLanguage().getCodeLanguage(),
                        translation.getDesignation(),
                        translation.getDescription())));
            } else {
                dto.setDesignation(null);
                dto.setDescription(null);
                dto.setTranslations(Collections.emptyList());
            }
        } else {
            dto.setDesignation(entity.getDesignation());
            dto.setDescription(entity.getDescription());
            dto.setTranslations(Collections.emptyList());
        }

        dto.setValues(entity.getRefDataValues().stream()
                .map(v -> refDataValueMapper.toDTOWithLang(v, lang))
                .collect(Collectors.toList()));

        return dto;
    }
}