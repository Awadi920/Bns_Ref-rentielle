package com.bns.bnsref.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "type_relation")
public class TypeRelation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(unique = true, nullable = false)
    @NaturalId // Indique que ce champ est une clé naturelle unique

    private String code; // Par exemple, "ONE_TO_MANY", "ONE_TO_ONE", "MANY_TO_ONE"

    private String description;

    @OneToMany(mappedBy = "typeRelation")
    @ToString.Exclude // Ajouté pour éviter la récursion
    private List<ListCodeRelation> listCodeRelations = new ArrayList<>();
}
