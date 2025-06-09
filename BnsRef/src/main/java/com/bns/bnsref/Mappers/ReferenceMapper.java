package com.bns.bnsref.Mappers;

import com.bns.bnsref.dto.*;
import com.bns.bnsref.Entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReferenceMapper {

    private final Ref_DataMapper refDataMapper;
    private final Ref_DataSpecMapper refDataSpecMapper;
    private final ListCodeRelationMapper listCodeRelationMapper;

    public ReferenceDTO toDTO(CodeList codeList) {
        if (codeList == null) return null;

        ReferenceDTO dto = new ReferenceDTO();
        dto.setCodeList(codeList.getCodeList());
        dto.setLabelList(codeList.getLabelList());
        dto.setDescription(codeList.getDescription());
        // Mapper les autres champs (domain, category, producer, refData, etc.)
        dto.setDomainCode(codeList.getDomain() != null ? codeList.getDomain().getCodeDomain() : null);
        dto.setCategoryCode(codeList.getCategory() != null ? codeList.getCategory().getCodeCategory() : null);
        dto.setProducerCode(codeList.getProducer() != null ? codeList.getProducer().getCodeProd() : null);
        dto.setRefData(codeList.getRefData().stream()
                .map(refDataMapper::toDTO) // Use Ref_DataMapper to map refData, including values
                .sorted(Comparator.comparing(Ref_DataDTO::getOrderPosition, Comparator.nullsLast(Integer::compareTo)))
                .collect(Collectors.toList()));
        // Les Ref_DataSpec sont chargés séparément dans ReferenceServiceImpl
        dto.setSourceRelations(codeList.getSourceRelations().stream()
                .map(listCodeRelationMapper::toDTO)
                .collect(Collectors.toList()));
        dto.setTargetRelations(codeList.getTargetRelations().stream()
                .map(listCodeRelationMapper::toDTO)
                .collect(Collectors.toList()));
        dto.setTranslations(codeList.getTranslations().stream()
                .map(t -> new CodeListTranslationDTO(
                        t.getCodeListTranslation(),
                        t.getCodeList() != null ? t.getCodeList().getCodeList() : null,
                        t.getLanguage().getCodeLanguage(),
                        t.getLabelList(),
                        t.getDescription() // Assumes CodeListTranslation has getDescription()
                ))
                .collect(Collectors.toList()));
        return dto;
    }

    public Ref_DataSpecDTO toRefDataSpecDTO(Ref_DataSpec refDataSpec) {
        return refDataSpecMapper.toDTO(refDataSpec);
    }
}