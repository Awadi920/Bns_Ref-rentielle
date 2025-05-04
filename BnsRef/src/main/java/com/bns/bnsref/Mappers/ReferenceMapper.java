package com.bns.bnsref.Mappers;

import com.bns.bnsref.dto.*;
import com.bns.bnsref.Entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

        dto.setDomainCode(codeList.getDomain() != null ? codeList.getDomain().getCodeDomain() : null);
        dto.setCategoryCode(codeList.getCategory() != null ? codeList.getCategory().getCodeCategory() : null);
        dto.setProducerCode(codeList.getProducer() != null ? codeList.getProducer().getCodeProd() : null);

        dto.setRefData(codeList.getRefData().stream()
                .map(refDataMapper::toDTO)
                .collect(Collectors.toList()));
        dto.setRefDataSpec(codeList.getRefDataSpec().stream()
                .map(refDataSpecMapper::toDTO)
                .collect(Collectors.toList()));

        dto.setSourceRelations(codeList.getSourceRelations().stream()
                .map(listCodeRelationMapper::toDTO)
                .collect(Collectors.toList()));
        dto.setTargetRelations(codeList.getTargetRelations().stream()
                .map(listCodeRelationMapper::toDTO)
                .collect(Collectors.toList()));

        return dto;
    }

//
//    private final Ref_DataMapper refDataMapper;
//    private final Ref_DataSpecMapper refDataSpecMapper;
//    private final ListCodeRelationMapper listCodeRelationMapper;
//
//    public ReferenceDTO toDTO(CodeList codeList) {
//        if (codeList == null) return null;
//
//        ReferenceDTO dto = new ReferenceDTO();
//        dto.setCodeList(codeList.getCodeList());
//        dto.setLabelList(codeList.getLabelList());
//        dto.setDescription(codeList.getDescription());
//        setRelations(dto, codeList, null);
//        return dto;
//    }
//
//    // Méthode surchargée pour gérer les cas sans withAllTranslations
//    private void setRelations(ReferenceDTO dto, CodeList codeList, String lang) {
//        setRelations(dto, codeList, lang, false); // Par défaut, withAllTranslations = false
//    }
//
//    // Méthode complète avec tous les paramètres
//    private void setRelations(ReferenceDTO dto, CodeList codeList, String lang, boolean withAllTranslations) {
//        dto.setDomainCode(codeList.getDomain() != null ? codeList.getDomain().getCodeDomain() : null);
//        dto.setCategoryCode(codeList.getCategory() != null ? codeList.getCategory().getCodeCategory() : null);
//        dto.setProducerCode(codeList.getProducer() != null ? codeList.getProducer().getCodeProd() : null);
//
//        if (withAllTranslations) {
//            dto.setRefData(codeList.getRefData().stream()
//                    .map(refDataMapper::toDTOWithAllTranslations)
//                    .collect(Collectors.toList()));
//            dto.setRefDataSpec(codeList.getRefDataSpec().stream()
//                    .map(refDataSpecMapper::toDTOWithAllTranslations)
//                    .collect(Collectors.toList()));
//        } else if (lang != null) {
//            dto.setRefData(codeList.getRefData().stream()
//                    .map(r -> refDataMapper.toDTOWithLang(r, lang))
//                    .collect(Collectors.toList()));
//            dto.setRefDataSpec(codeList.getRefDataSpec().stream()
//                    .map(r -> refDataSpecMapper.toDTOWithLang(r, lang))
//                    .collect(Collectors.toList()));
//        } else {
//            dto.setRefData(codeList.getRefData().stream()
//                    .map(refDataMapper::toDTO)
//                    .collect(Collectors.toList()));
//            dto.setRefDataSpec(codeList.getRefDataSpec().stream()
//                    .map(refDataSpecMapper::toDTO)
//                    .collect(Collectors.toList()));
//        }
//
//        dto.setSourceRelations(codeList.getSourceRelations().stream()
//                .map(listCodeRelationMapper::toDTO)
//                .collect(Collectors.toList()));
//        dto.setTargetRelations(codeList.getTargetRelations().stream()
//                .map(listCodeRelationMapper::toDTO)
//                .collect(Collectors.toList()));
//    }
//
//
//    public ReferenceDTO toDTOWithAllTranslations(CodeList codeList) {
//        if (codeList == null) return null;
//
//        ReferenceDTO dto = new ReferenceDTO();
//        dto.setCodeList(codeList.getCodeList());
//        dto.setLabelList(codeList.getLabelList());
//        dto.setDescription(codeList.getDescription());
//
//        dto.setTranslations(codeList.getTranslations().stream()
//                .map(t -> new CodeListTranslationDTO(
//                        t.getCodeListTranslation(),
//                        t.getCodeList().getCodeList(),
//                        t.getLanguage().getCodeLanguage(),
//                        t.getLabelList(),
//                        t.getDescription()))
//                .collect(Collectors.toList()));
//
//        setRelations(dto, codeList, null, true); // Appel avec withAllTranslations = true
//        return dto;
//    }
//
//    public ReferenceDTO toDTOWithLang(CodeList codeList, String lang) {
//        if (codeList == null) return null;
//
//        ReferenceDTO dto = new ReferenceDTO();
//        dto.setCodeList(codeList.getCodeList());
//
//        if (lang != null) {
//            CodeListTranslation translation = codeList.getTranslations().stream()
//                    .filter(t -> t.getLanguage().getCodeLanguage().equalsIgnoreCase(lang))
//                    .findFirst()
//                    .orElse(null);
//            if (translation != null) {
//                dto.setLabelList(translation.getLabelList());
//                dto.setDescription(translation.getDescription());
//            } else {
//                dto.setLabelList(codeList.getLabelList());
//                dto.setDescription(codeList.getDescription());
//            }
//        } else {
//            dto.setLabelList(codeList.getLabelList());
//            dto.setDescription(codeList.getDescription());
//        }
//
//        setRelations(dto, codeList, lang); // Appel corrigé
//        return dto;
//    }
//
//
//
//    public List<ReferenceDTO> toDTOList(List<CodeList> codeLists) {
//        return codeLists.stream().map(this::toDTO).collect(Collectors.toList());
//    }
//
//    public List<ReferenceDTO> toDTOListWithAllTranslations(List<CodeList> codeLists) {
//        return codeLists.stream().map(this::toDTOWithAllTranslations).collect(Collectors.toList());
//    }
//
//    public List<ReferenceDTO> toDTOListWithLang(List<CodeList> codeLists, String lang) {
//        return codeLists.stream().map(cl -> toDTOWithLang(cl, lang)).collect(Collectors.toList());
//    }
}