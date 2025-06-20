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
public class Ref_DataSpec implements Serializable {
    @Id
    private String codeRefDataSpec;
    private String designation;
    private String description;
    private Integer orderPosition; // Nouveau champ pour l'ordre

    // relation with codelist *--> 1 bidirectional
    @ManyToOne
    @JoinColumn(name = "code_list_code_list", nullable = false)
    private CodeList codeList;

    @OneToMany(mappedBy = "refDataSpec", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Builder.Default // Lombok: initialise la collection
    private List<Ref_DataSpecValue> refDataSpecValues = new ArrayList<>();

    @OneToMany(mappedBy = "refDataSpec", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Ref_DataSpecTranslation> translations = new HashSet<>();
}
