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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ref_Data implements Serializable {

    @Id
    private String codeRefData;
    private String designation;
    private String description;

    @ManyToOne
    private CodeList codeList;

    @OneToMany(mappedBy = "refData", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Ref_DataValue> refDataValues = new HashSet<>();


}

