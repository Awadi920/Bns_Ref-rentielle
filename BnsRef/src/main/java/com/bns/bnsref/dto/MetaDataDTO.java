package com.bns.bnsref.dto;

import lombok.*;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaDataDTO {
    private String codeMetaData;
    private String nameMetaData;
    private String valueMetaData;
    private Date lastUpdate;
    private String standard;
    private String codeList; // Représente la clé étrangère (codeList)
}
