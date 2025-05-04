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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code_list", "code_langue"})) // elle garantit que pour une entité comme CodeListTranslation, il ne peut pas y avoir deux enregistrements avec la même combinaison de code_list et code_langue
public class CodeListTranslation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codeListTranslation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_list", nullable = false)
    private CodeList codeList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_langue", nullable = false)
    private Language language;

    private String labelList;
    private String description;

}
