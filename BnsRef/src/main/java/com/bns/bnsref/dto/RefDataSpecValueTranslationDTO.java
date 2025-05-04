package com.bns.bnsref.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefDataSpecValueTranslationDTO {

    private Long codeRefDataSpecValueTranslation;
    private String codeRefDataSpecValue; // Code de Ref_DataSpecValue
    private String languageCode;         // Code de la langue
    private String value;
}
