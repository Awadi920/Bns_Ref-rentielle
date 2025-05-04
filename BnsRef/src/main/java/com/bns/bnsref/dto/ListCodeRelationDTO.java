package com.bns.bnsref.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListCodeRelationDTO {

    private Long idRelation;
    private String codeListSourceCode; // Code de la CodeList source
    private String codeListCibleCode; // Code de la CodeList cible
    //private TypeRelation typeRelation;
    private String typeRelationCode; // Code du TypeRelation (ex. "ONE_TO_MANY")
    private String description;
}