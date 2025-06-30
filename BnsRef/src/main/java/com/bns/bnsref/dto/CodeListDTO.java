package com.bns.bnsref.dto;

import com.bns.bnsref.validation.UniqueLabelList;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeListDTO {
    private String codeList;
    @UniqueLabelList
    private String labelList;
    private String description;
    private LocalDateTime creationDate;

    private boolean translated;

    // Identifiants des entités associées
    private String domainCode;
    private String codeCategory;  // Correct en camelCase
    private String producerCode;

}
