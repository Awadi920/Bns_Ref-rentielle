package com.bns.bnsref.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ref_DataSpecValueDTO {
    private String codeRefDataSpecValue;
    private String value;
    private String codeRefDataSpec; // Stocke l'ID de Ref_DataSpec
}
