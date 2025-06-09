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


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE})
    @JoinTable(
            name = "ref_data_value_parent_child",
            joinColumns = @JoinColumn(name = "child_id", referencedColumnName = "codeRefDataValue"),
            inverseJoinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "codeRefDataValue")
    )
    @JsonManagedReference
    private Set<Ref_DataValue> parents = new HashSet<>();

    @ManyToMany(mappedBy = "parents", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JsonBackReference
    @JsonManagedReference
    private Set<Ref_DataValue> children = new HashSet<>();

    @OneToMany(mappedBy = "refDataValue",cascade = CascadeType.REMOVE , fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Ref_DataValueTranslation> translations = new HashSet<>();
}