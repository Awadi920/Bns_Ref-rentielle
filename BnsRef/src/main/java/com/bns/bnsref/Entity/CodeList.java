package com.bns.bnsref.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CodeList implements Serializable {

    @Id
    private String codeList;

    private String labelList;
    private String description;

    //@Temporal(TemporalType.DATE)
    //@UpdateTimestamp
    private LocalDateTime creationDate;

    // relation *--1 with domain bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_code")
    private Domain domain;

    // relation *--1 with category bidirectional
    @ManyToOne(fetch = FetchType.LAZY) // u
    @JoinColumn(name = "code_category")
    private Category category;

    //relation *--1 with Producer bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_code")
    private Producer producer;

    // relation 1-->* with Refdata bidirectional
    @OneToMany(mappedBy = "codeList", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Ref_Data> refData = new HashSet<>();

    // relation 1-->* with RefDataSpec bidirectional
    @OneToMany(mappedBy = "codeList", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Ref_DataSpec> refDataSpec = new HashSet<>();

    // Nouvelle relation avec MetaData
    @OneToMany(mappedBy = "codeList", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<MetaData> metaData = new HashSet<>();


    // relation 1-->* with CodeListTranslation bidirectional
    @OneToMany(mappedBy = "codeList", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<CodeListTranslation> translations = new HashSet<>();


    // relation 1-->* with ListCodeRelation bidirectional
    @OneToMany(mappedBy = "codeListSource", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<ListCodeRelation> sourceRelations = new HashSet<>();

    @OneToMany(mappedBy = "codeListCible", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<ListCodeRelation> targetRelations = new HashSet<>();


    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDateTime.now();
    }
}
