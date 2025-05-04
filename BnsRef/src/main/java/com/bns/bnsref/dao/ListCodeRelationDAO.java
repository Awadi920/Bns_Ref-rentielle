package com.bns.bnsref.dao;

import com.bns.bnsref.Entity.ListCodeRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListCodeRelationDAO extends JpaRepository<ListCodeRelation, Long> {
    List<ListCodeRelation> findByCodeListSourceCodeList(String codeListSource);
    List<ListCodeRelation> findByCodeListCibleCodeList(String codeListCible);
}
