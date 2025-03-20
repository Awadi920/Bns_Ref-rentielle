package com.bns.bnsref.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // <-- Ajouté
public class Producer implements Serializable {
    @Id
    @Column(name = "code_prod")
    private String codeProd;
    private String name;
    private String address;
    private String city;
    private String phone;
    private String email;
    private String website;

    //relation 1-->* with ListCode bidirectional
    @OneToMany(mappedBy = "producer")
    @ToString.Exclude // <-- Exclure de toString()
    private Set<CodeList> codeLists = new HashSet<>();

}
