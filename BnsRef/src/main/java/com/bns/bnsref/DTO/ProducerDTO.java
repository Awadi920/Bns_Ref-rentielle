package com.bns.bnsref.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProducerDTO {
    private String codeProd;
    private String name;
    private String address;
    private String city;
    private String phone;
    private String email;
    private String website;
}
