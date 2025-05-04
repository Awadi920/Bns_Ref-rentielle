package com.bns.bnsref.Mappers;


import com.bns.bnsref.dto.ListCodeRelationDTO;
import com.bns.bnsref.Entity.ListCodeRelation;
import org.springframework.stereotype.Component;

@Component
public class ListCodeRelationMapper {

    public ListCodeRelationDTO toDTO(ListCodeRelation relation) {
        if (relation == null) return null;

        ListCodeRelationDTO dto = new ListCodeRelationDTO();
        dto.setIdRelation(relation.getIdRelation());
        dto.setCodeListSourceCode(relation.getCodeListSource() != null ? relation.getCodeListSource().getCodeList() : null);
        dto.setCodeListCibleCode(relation.getCodeListCible() != null ? relation.getCodeListCible().getCodeList() : null);
        dto.setTypeRelationCode(relation.getTypeRelation() != null ? relation.getTypeRelation().getCode() : null);
        dto.setDescription(relation.getDescription());
        return dto;
    }

    public ListCodeRelation toEntity(ListCodeRelationDTO dto) {
        if (dto == null) return null;

        ListCodeRelation relation = new ListCodeRelation();
        relation.setDescription(dto.getDescription());
        // Les relations (codeListSource, codeListCible et typeRelation) seront gérées dans le service
        return relation;
    }
}