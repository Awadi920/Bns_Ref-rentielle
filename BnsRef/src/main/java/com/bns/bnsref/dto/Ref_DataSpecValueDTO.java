package com.bns.bnsref.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ref_DataSpecValueDTO {
    private String codeRefDataSpecValue;
    private String value;
    private String codeRefDataSpec; // Stocke l'ID de Ref_DataSpec
    private String refDataValueCode; // Ajout pour lier à Ref_DataValue

    @Builder.Default
    private List<RefDataSpecValueTranslationDTO> translations = new ArrayList<>(); // Ajout des traductions
}
