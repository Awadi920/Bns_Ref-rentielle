package com.bns.bnsref.Service;

import com.bns.bnsref.dto.ListCodeRelationDTO;

import java.util.List;

public interface ListCodeRelationService {

    ListCodeRelationDTO createListCodeRelation(ListCodeRelationDTO dto);
    ListCodeRelationDTO updateListCodeRelation(Long id, ListCodeRelationDTO dto);
    ListCodeRelationDTO getListCodeRelationById(Long id);
    List<ListCodeRelationDTO> getAllListCodeRelations();
    void deleteListCodeRelation(Long id);
}
