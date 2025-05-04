package com.bns.bnsref.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeListTranslationDTO {

    private Long codeListTranslation;
    private String codeListCode; // Code de la CodeList associée
    private String languageCode; // Code de la langue
    private String labelList;
    private String description;
}
