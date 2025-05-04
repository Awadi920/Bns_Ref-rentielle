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
public class Ref_DataTranslation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codeRefDataTranslation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_ref_data", nullable = false)
    private Ref_Data refData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_langue", nullable = false)
    private Language language;

    private String designation;
    private String description;


}
