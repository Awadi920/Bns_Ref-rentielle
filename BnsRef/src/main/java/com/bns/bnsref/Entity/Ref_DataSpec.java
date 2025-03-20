package com.bns.bnsref.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // relation with codelist *--> 1 bidirectional
    @ManyToOne
    private CodeList codeList;

    @OneToMany(mappedBy = "refDataSpec", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Builder.Default // Lombok: initialise la collection
    private List<Ref_DataSpecValue> refDataSpecValues = new ArrayList<>();

}
