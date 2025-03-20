package com.bns.bnsref.Mappers;

import com.bns.bnsref.DTO.Ref_DataDTO;
import com.bns.bnsref.DTO.Ref_DataSpecDTO;
import com.bns.bnsref.DTO.ReferenceDTO;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Entity.Ref_Data;
import com.bns.bnsref.Entity.Ref_DataSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReferenceMapper {

    private final Ref_DataValueMapper refDataValueMapper;
    private final Ref_DataSpecValueMapper refDataSpecValueMapper;

    private final Ref_DataMapper refDataMapper;       // Mapper pour Ref_Data
    private final Ref_DataSpecMapper refDataSpecMapper; // Mapper pour Ref_DataSpec


    public ReferenceDTO toDTO(CodeList codeList) {
        return ReferenceDTO.builder()
                .codeList(codeList.getCodeList())
                .labelList(codeList.getLabelList())
                .description(codeList.getDescription())
                .refData(codeList.getRefData().stream()
                        .map(refDataMapper::toDTO)
                        .collect(Collectors.toList()))
                .refDataSpec(codeList.getRefDataSpec().stream()
                        .map(refDataSpecMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

//    public ReferenceDTO toDTO(CodeList codeList) {
//        return ReferenceDTO.builder()
//                .codeList(codeList.getCodeList())
//                .labelList(codeList.getLabelList())
//                .description(codeList.getDescription())
//                .refData(codeList.getRefData().stream()
//                        .map(this::mapRefData)
//                        .collect(Collectors.toList()))
//                .refDataSpec(codeList.getRefDataSpec().stream()
//                        .map(this::mapRefDataSpec)
//                        .collect(Collectors.toList()))
//                .build();
//    }
//
//    private Ref_DataDTO mapRefData(Ref_Data refData) {
//        return Ref_DataDTO.builder()
//                .codeRefData(refData.getCodeRefData())
//                .designation(refData.getDesignation())
//                .description(refData.getDescription())
//                .codeListCode(refData.getCodeList().getCodeList())
//                .values(refData.getRefDataValues().stream()
//                        .map(refDataValueMapper::toDTO)
//                        .collect(Collectors.toList()))
//                .build();
//    }
//
//    private Ref_DataSpecDTO mapRefDataSpec(Ref_DataSpec refDataSpec) {
//        return Ref_DataSpecDTO.builder()
//                .codeRefDataSpec(refDataSpec.getCodeRefDataSpec())
//                .designation(refDataSpec.getDesignation())
//                .description(refDataSpec.getDescription())
//                .codeListCode(refDataSpec.getCodeList().getCodeList())
//                .specValues(refDataSpec.getRefDataSpecValues().stream()
//                        .map(refDataSpecValueMapper::toDTO)
//                        .collect(Collectors.toList()))
//                .build();
//    }
}