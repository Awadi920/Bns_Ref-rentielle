package com.bns.bnsref.dto;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DomainDTO {
    private String codeDomain;
    private String domainName;
    private String domainDescription;
}
