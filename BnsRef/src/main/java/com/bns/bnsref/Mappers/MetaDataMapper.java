package com.bns.bnsref.Mappers;

import com.bns.bnsref.DTO.MetaDataDTO;
import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Entity.MetaData;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class MetaDataMapper {

    public MetaData toEntity(MetaDataDTO dto, CodeList codeList) {
        return MetaData.builder()
                .nameMetaData(dto.getNameMetaData())
                .valueMetaData(dto.getValueMetaData())
                .lastUpdate(dto.getLastUpdate())
                .standard(dto.getStandard())
                .codeList(codeList)
                .build();
    }

    public MetaDataDTO toDTO(MetaData entity) {
        return MetaDataDTO.builder()
                .codeMetaData(entity.getCodeMetaData())
                .nameMetaData(entity.getNameMetaData())
                .valueMetaData(entity.getValueMetaData())
                .lastUpdate(entity.getLastUpdate())
                .standard(entity.getStandard())
                .codeList(entity.getCodeList().getCodeList()) // Code de la CodeList
                .build();
    }
}
