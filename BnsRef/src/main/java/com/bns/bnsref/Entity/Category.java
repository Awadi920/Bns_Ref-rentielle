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
public class Category implements Serializable {
    @Id
    @Column(name = "code_category")
    private String codeCategory;
    private String categoryName;
    private String categoryDescription;

    // relation 1-->* with ListCode bidirectional
    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private Set<CodeList> codeLists = new HashSet<>();
}
