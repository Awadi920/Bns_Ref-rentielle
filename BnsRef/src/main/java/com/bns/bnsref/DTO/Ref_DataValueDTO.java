package com.bns.bnsref.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ref_DataValueDTO {
    private String codeRefDataValue;
    private String value;
    private String codeRefData; // Stocke l'ID de Ref_Data
    private String codeLanguage; // Stocke l'ID de Language

}