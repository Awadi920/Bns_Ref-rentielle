package com.bns.bnsref.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Domain implements Serializable  {
    @Id
    @Column(name = "code_domain")
    private String codeDomain;
    private String domainName;
    private String domainDescription;

    //relation with codelist 1-->* bidirectional
    @OneToMany(mappedBy = "domain")
    @ToString.Exclude
    private Set<CodeList> codeLists = new HashSet<>();

}
