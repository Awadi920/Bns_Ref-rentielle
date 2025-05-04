package com.bns.bnsref.Filter;

import com.bns.bnsref.Filter.Enums.SortDirection;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SortCriteria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityName;
    private String fieldName;
    @Enumerated(EnumType.STRING)
    private SortDirection direction;


    @Column(updatable = false)
    private LocalDateTime creationDate;


    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
    }
}