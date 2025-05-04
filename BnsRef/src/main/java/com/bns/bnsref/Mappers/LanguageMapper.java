package com.bns.bnsref.Mappers;

import com.bns.bnsref.dto.LanguageDTO;
import com.bns.bnsref.Entity.Language;
import org.springframework.stereotype.Component;

@Component
public class LanguageMapper {

    public Language toEntity(LanguageDTO dto) {
        return Language.builder()
                .codeLanguage(dto.getCodeLanguage())
                .languageName(dto.getLanguageName())
                .build();
    }

    public LanguageDTO toDTO(Language entity) {
        return LanguageDTO.builder()
                .codeLanguage(entity.getCodeLanguage())
                .languageName(entity.getLanguageName())
                .build();
    }
}
