package com.bns.bnsref.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ref_DataSpecValue implements Serializable {
    @Id
    private String codeRefDataSpecValue;
    private String value;

    // relation with Ref_DataSpec *--> 1 bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    private Ref_DataSpec refDataSpec;

}
