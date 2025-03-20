package com.bns.bnsref.DTO;

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
