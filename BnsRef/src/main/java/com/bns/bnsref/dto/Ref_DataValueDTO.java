package com.bns.bnsref.dto;

import lombok.*;

import java.util.ArrayList;
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

    // Code de la valeur parent
    private String parentValueCode;

    private List<String> childValueCodes; // Nouveau champ pour recevoir les codes des enfants
    private List<Ref_DataValueDTO> childValues= new ArrayList<>(); // Pas inclus dans hashCode/equals

    @Builder.Default
    private List<RefDataValueTranslationDTO> translations = new ArrayList<>(); // Ajout des traductions

}