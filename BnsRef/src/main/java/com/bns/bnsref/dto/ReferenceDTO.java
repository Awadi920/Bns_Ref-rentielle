package com.bns.bnsref.dto;

import com.bns.bnsref.Views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReferenceDTO {
    @JsonView(Views.Detailed.class)
    private String codeList;

    @JsonView(Views.Detailed.class)
    private String labelList;

    @JsonView(Views.Detailed.class)
    private String description;

    // Nouvel attribut boolean
    @JsonView(Views.Detailed.class)
    private boolean translated;

    // Relations
    private String domainCode; // Code du domaine
    private String categoryCode; // Code de la catégorie
    private String producerCode; // Code du producteur

    @JsonView(Views.Detailed.class)
    @Builder.Default
    private List<Ref_DataDTO> refData = new ArrayList<>();

    @JsonView(Views.Detailed.class)
    @Builder.Default
    private List<Ref_DataSpecDTO> refDataSpec = new ArrayList<>();

    // Relations entre CodeList
    @Builder.Default
    private List<ListCodeRelationDTO> sourceRelations = new ArrayList<>();

    @Builder.Default
    private List<ListCodeRelationDTO> targetRelations = new ArrayList<>();

    // Ajout des traductions
    @JsonView(Views.Detailed.class)
    @Builder.Default
    private List<CodeListTranslationDTO> translations = new ArrayList<>();

}
