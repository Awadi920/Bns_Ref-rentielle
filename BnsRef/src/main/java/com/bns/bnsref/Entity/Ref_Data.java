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
public class Ref_Data implements Serializable {

    @Id
    @EqualsAndHashCode.Include // Ajout pour cohérence
    private String codeRefData;
    private String designation;
    private String description;

    @ManyToOne
    private CodeList codeList;

    @OneToMany(mappedBy = "refData", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Ref_DataValue> refDataValues = new HashSet<>();


    //  relation 1-->* with RefDataTranslation bidirectional
    @OneToMany(mappedBy = "refData", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Ref_DataTranslation> translations = new HashSet<>();

}

