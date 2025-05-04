package com.bns.bnsref.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code_list_source", "code_list_cible", "type_relation"}))
public class ListCodeRelation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRelation; // Changé de String à Long, avec auto-incrémentation

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_list_source", nullable = false)
    @ToString.Exclude // Ajouté pour éviter la récursion
    private CodeList codeListSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_list_cible", nullable = false)
    @ToString.Exclude // Ajouté pour éviter la récursion
    private CodeList codeListCible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_relation", nullable = false, referencedColumnName = "code") // Référencer la colonne "code"
    @ToString.Exclude // Ajouté pour éviter la récursion

    private TypeRelation typeRelation;

}



