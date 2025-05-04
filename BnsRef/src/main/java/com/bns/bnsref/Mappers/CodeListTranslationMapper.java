package com.bns.bnsref.Mappers;


import com.bns.bnsref.dto.CodeListTranslationDTO;
import com.bns.bnsref.Entity.CodeListTranslation;
import org.springframework.stereotype.Component;


@Component
public class CodeListTranslationMapper {

    public CodeListTranslationDTO toDTO(CodeListTranslation translation) {
        if (translation == null) return null;

        CodeListTranslationDTO dto = new CodeListTranslationDTO();
        dto.setCodeListTranslation(translation.getCodeListTranslation());
        // Inclure uniquement le code de la CodeList, pas l'objet complet
        dto.setCodeListCode(translation.getCodeList() != null ? translation.getCodeList().getCodeList() : null);
        // Inclure uniquement le code de la Language, pas l'objet complet
        dto.setLanguageCode(translation.getLanguage() != null ? translation.getLanguage().getCodeLanguage() : null);
        dto.setLabelList(translation.getLabelList());
        dto.setDescription(translation.getDescription());

        return dto;
    }

    public CodeListTranslation toEntity(CodeListTranslationDTO dto) {
        if (dto == null) return null;

        CodeListTranslation translation = new CodeListTranslation();
        translation.setCodeListTranslation(dto.getCodeListTranslation());
        translation.setLabelList(dto.getLabelList());
        translation.setDescription(dto.getDescription());
        // Les relations (codeList et language) seront gérées dans le service
        return translation;
    }
}