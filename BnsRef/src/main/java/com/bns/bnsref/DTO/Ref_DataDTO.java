package com.bns.bnsref.DTO;

import com.bns.bnsref.Views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ref_DataDTO {
    @JsonView(Views.Basic.class)
    private String codeRefData;

    @JsonView(Views.Basic.class)
    private String designation;

    @JsonView(Views.Basic.class)
    private String description;

    @JsonView(Views.Basic.class)
    private String codeListCode;

    @JsonView(Views.Detailed.class)
    private List<Ref_DataValueDTO> values;
}
