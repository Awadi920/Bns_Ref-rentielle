package com.bns.bnsref.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaData implements Serializable {
    @Id
    private String codeMetaData;
    private String nameMetaData;
    private String valueMetaData;
    @Temporal(TemporalType.DATE)
    private Date lastUpdate;
    private String standard;


    @ManyToOne
    @JoinColumn(name = "codeList") //foreign key
    private CodeList codeList;

}
