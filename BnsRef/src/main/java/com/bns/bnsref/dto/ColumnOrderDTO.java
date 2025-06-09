package com.bns.bnsref.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnOrderDTO {
    private String code; // codeRefData ou codeRefDataSpec
    private String type; // "REF_DATA" ou "REF_DATA_SPEC"
    private Integer orderPosition;
}
