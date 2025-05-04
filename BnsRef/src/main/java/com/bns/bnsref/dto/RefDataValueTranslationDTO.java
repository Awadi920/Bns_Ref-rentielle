package com.bns.bnsref.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefDataValueTranslationDTO {

    private Long codeRefDataValueTranslation;
    private String codeRefDataValue; // Code de Ref_DataValue
    private String languageCode;     // Code de la langue
    private String value;
}