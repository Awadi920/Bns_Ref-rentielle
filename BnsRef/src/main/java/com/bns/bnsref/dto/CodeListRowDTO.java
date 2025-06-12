package com.bns.bnsref.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeListRowDTO {
    private String rowId; // Identifiant unique de la ligne
    private String codeListId; // Identifiant du CodeList
    @Builder.Default
    private List<Ref_DataValueDTO> refDataValues = new ArrayList<>();
    @Builder.Default
    private List<Ref_DataSpecValueDTO> refDataSpecValues = new ArrayList<>();
}
