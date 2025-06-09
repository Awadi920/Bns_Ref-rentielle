package com.bns.bnsref.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ref_DataValueDTO {
    private String codeRefDataValue;
    private String value; // Traduit selon la langue demandée

    // Code de la Ref_Data associée
    private String codeRefData;

    private List<String> parentValueCodes;
    private String parentValueCode; // Added for backward compatibility
    private List<String> childValueCodes;

    @Builder.Default
    private List<RefDataValueTranslationDTO> translations = new ArrayList<>(); // Ajout des traductions

    // Custom getter to handle both parentValueCode and parentValueCodes
    public List<String> getParentValueCodes() {
        if (parentValueCodes != null && !parentValueCodes.isEmpty()) {
            return parentValueCodes;
        } else if (parentValueCode != null) {
            return Collections.singletonList(parentValueCode);
        }
        return new ArrayList<>();
    }
}