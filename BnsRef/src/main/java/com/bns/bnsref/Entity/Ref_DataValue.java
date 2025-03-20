package com.bns.bnsref.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ref_DataValue {
    @Id
    private String codeRefDataValue;
    private String value;

    // relation *-->1 with Ref_Data bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    private Ref_Data refData;

    // relation 1-->* with Language bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    private Language language;

}
