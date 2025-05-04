package com.bns.bnsref.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ref_DataValue {
    @Id
    @EqualsAndHashCode.Include // Inclure uniquement l'ID dans hashCode/equals
    private String codeRefDataValue;
    private String value;

    // relation *-->1 with Ref_Data bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    private Ref_Data refData;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_value_code")
    @JsonBackReference
    private Ref_DataValue parentValue;

    @OneToMany(mappedBy = "parentValue", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    private Set<Ref_DataValue> childValues = new HashSet<>();

    @OneToMany(mappedBy = "refDataValue",cascade = CascadeType.REMOVE , fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Ref_DataValueTranslation> translations = new HashSet<>();
}