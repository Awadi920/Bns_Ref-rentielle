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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code_ref_data_spec", "code_langue"}))
public class Ref_DataSpecTranslation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codeRefDataSpecTranslation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_ref_data_spec", nullable = false)
    private Ref_DataSpec refDataSpec;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_langue", nullable = false)
    private Language language;

    private String designation;
    private String description;

}
