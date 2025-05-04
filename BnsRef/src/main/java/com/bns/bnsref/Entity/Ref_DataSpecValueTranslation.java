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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"code_ref_data_spec_value", "code_langue"}))
public class Ref_DataSpecValueTranslation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codeRefDataSpecValueTranslation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_ref_data_spec_value", nullable = false)
    private Ref_DataSpecValue refDataSpecValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_langue", nullable = false)
    private Language language;

    private String value;

}
