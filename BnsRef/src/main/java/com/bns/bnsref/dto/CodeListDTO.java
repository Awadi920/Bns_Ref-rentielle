package com.bns.bnsref.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeListDTO {
    private String codeList;
    private String labelList;
    private String description;
    private LocalDateTime creationDate;

    // Identifiants des entités associées
    private String domainCode;
    private String codeCategory;  // Correct en camelCase
    private String producerCode;

}
