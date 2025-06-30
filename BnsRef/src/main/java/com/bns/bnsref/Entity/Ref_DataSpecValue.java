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
public class Ref_DataSpecValue implements Serializable {
    @Id
    private String codeRefDataSpecValue;
    private String value;

    // Nouveau champ
    @Column(name = "row_id")
    private String rowId; // Identifiant de la ligne pour regrouper les valeurs

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_langue")
    private Language language; // Relation avec Language


    // relation with Ref_DataSpec *--> 1 bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    private Ref_DataSpec refDataSpec;

    // relation with RefDataSpecValueTranslation 1 --> * bidirectional
    @OneToMany(mappedBy = "refDataSpecValue", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Ref_DataSpecValueTranslation> translations = new HashSet<>();

    // Associer le Ref_DataValue
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refDataValueAssocie") // Ajout de la relation
    private Ref_DataValue refDataValue;
}
