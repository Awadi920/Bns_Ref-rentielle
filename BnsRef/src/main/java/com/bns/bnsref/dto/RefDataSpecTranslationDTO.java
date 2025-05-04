package com.bns.bnsref.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefDataSpecTranslationDTO {

    private Long codeRefDataSpecTranslation;
    private String codeRefDataSpec; // Code de Ref_DataSpec
    private String languageCode;    // Code de la langue
    private String designation;
    private String description;
}
