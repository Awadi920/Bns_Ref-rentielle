package com.bns.bnsref.DTO;

import com.bns.bnsref.Views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ReferenceDTO {
    @JsonView(Views.Detailed.class)
    private String codeList;

    @JsonView(Views.Detailed.class)
    private String labelList;

    @JsonView(Views.Detailed.class)
    private String description;

    @JsonView(Views.Detailed.class)
    private List<Ref_DataDTO> refData;

    @JsonView(Views.Detailed.class)
    private List<Ref_DataSpecDTO> refDataSpec;

    // Getters et Setters
    public String getCodeList() {
        return codeList;
    }

    public void setCodeList(String codeList) {
        this.codeList = codeList;
    }

    public String getLabelList() {
        return labelList;
    }

    public void setLabelList(String labelList) {
        this.labelList = labelList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ref_DataDTO> getRefData() {
        return refData;
    }

    public void setRefData(List<Ref_DataDTO> refData) {
        this.refData = refData;
    }

    public List<Ref_DataSpecDTO> getRefDataSpec() {
        return refDataSpec;
    }

    public void setRefDataSpec(List<Ref_DataSpecDTO> refDataSpec) {
        this.refDataSpec = refDataSpec;
    }
}
