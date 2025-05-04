package com.bns.bnsref.Mappers;


import com.bns.bnsref.dto.RefDataSpecTranslationDTO;
import com.bns.bnsref.dto.Ref_DataSpecDTO;
import com.bns.bnsref.Entity.Ref_DataSpecTranslation;
import com.bns.bnsref.Entity.Ref_DataSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class Ref_DataSpecMapper {

    private final Ref_DataSpecValueMapper refDataSpecValueMapper;

    public Ref_DataSpecDTO toDTO(Ref_DataSpec entity) {
        if (entity == null) return null;

        Ref_DataSpecDTO dto = Ref_DataSpecDTO.builder()
                .codeRefDataSpec(entity.getCodeRefDataSpec())
                .designation(entity.getDesignation())
                .description(entity.getDescription())
                .codeListCode(entity.getCodeList() != null ? entity.getCodeList().getCodeList() : null)
                .build();

        dto.setSpecValues(entity.getRefDataSpecValues().stream()
                .map(refDataSpecValueMapper::toDTO)
                .distinct()
                .collect(Collectors.toList()));

        return dto;
    }

    public Ref_DataSpec toEntity(Ref_DataSpecDTO dto) {
        if (dto == null) return null;

        return Ref_DataSpec.builder()
                .codeRefDataSpec(dto.getCodeRefDataSpec())
                .designation(dto.getDesignation())
                .description(dto.getDescription())
                .build();
    }

    public Ref_DataSpecDTO toDTOWithAllTranslations(Ref_DataSpec entity) {
        if (entity == null) return null;

        Ref_DataSpecDTO dto = toDTO(entity);
        dto.setTranslations(entity.getTranslations().stream()
                .map(t -> new RefDataSpecTranslationDTO(
                        t.getCodeRefDataSpecTranslation(),
                        t.getRefDataSpec().getCodeRefDataSpec(),
                        t.getLanguage().getCodeLanguage(),
                        t.getDesignation(),
                        t.getDescription()))
                .collect(Collectors.toList()));
        dto.setSpecValues(entity.getRefDataSpecValues().stream()
                .map(refDataSpecValueMapper::toDTOWithAllTranslations)
                .distinct()
                .collect(Collectors.toList()));
        return dto;
    }

    public Ref_DataSpecDTO toDTOWithLang(Ref_DataSpec entity, String lang) {
        if (entity == null) return null;

        Ref_DataSpecDTO dto = new Ref_DataSpecDTO(); // Ne pas appeler toDTO pour éviter les valeurs par défaut
        dto.setCodeRefDataSpec(entity.getCodeRefDataSpec());
        dto.setCodeListCode(entity.getCodeList() != null ? entity.getCodeList().getCodeList() : null);

        if (lang != null) {
            Ref_DataSpecTranslation translation = entity.getTranslations().stream()
                    .filter(t -> t.getLanguage().getCodeLanguage().equalsIgnoreCase(lang))
                    .findFirst()
                    .orElse(null);
            if (translation != null) {
                dto.setDesignation(translation.getDesignation());
                dto.setDescription(translation.getDescription());
                // Remplir translations avec la traduction trouvée
                dto.setTranslations(List.of(new RefDataSpecTranslationDTO(
                        translation.getCodeRefDataSpecTranslation(),
                        translation.getRefDataSpec().getCodeRefDataSpec(),
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

        dto.setSpecValues(entity.getRefDataSpecValues().stream()
                .map(v -> refDataSpecValueMapper.toDTOWithLang(v, lang))
                .distinct()
                .collect(Collectors.toList()));

        return dto;
    }
}