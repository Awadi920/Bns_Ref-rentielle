package com.bns.bnsref.dao;

import com.bns.bnsref.Entity.Ref_Data;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface Ref_DataDAO extends JpaRepository<Ref_Data, String>, JpaSpecificationExecutor<Ref_Data> {
    @Query("SELECT r.codeRefData FROM Ref_Data r ORDER BY r.codeRefData DESC LIMIT 1")
    Optional<String> findLastRefDataCode(); // Récupérer le dernier codeRefData


    @EntityGraph(attributePaths = {"codeList"}) // Fetch codeList for Ref_DataDTO
    Page<Ref_Data> findAll(Pageable pageable); // Changed to standard findAll


    @Query("SELECT r FROM Ref_Data r WHERE r.codeList.codeList = :codeListCode")
    List<Ref_Data> findByCodeListCodeList(String codeListCode);
}
