package com.bns.bnsref.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefDataTranslationDTO {
    private Long codeRefDataTranslation;
    private String codeRefData; // Code de Ref_Data
    private String languageCode; // Code de la langue
    private String designation;
    private String description;


}
