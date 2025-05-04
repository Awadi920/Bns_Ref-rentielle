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
public class Ref_DataSpecDTO {

    @JsonView(Views.Basic.class)
    private String codeRefDataSpec;

    @JsonView(Views.Basic.class)
    private String designation;

    @JsonView(Views.Basic.class)
    private String description;

    @JsonView(Views.Basic.class)
    private String codeListCode;

    @JsonView(Views.Detailed.class)
    @Builder.Default
    private List<Ref_DataSpecValueDTO> specValues= new ArrayList<>();

    @Builder.Default
    private List<RefDataSpecTranslationDTO> translations = new ArrayList<>(); // Ajout des traductions
}
