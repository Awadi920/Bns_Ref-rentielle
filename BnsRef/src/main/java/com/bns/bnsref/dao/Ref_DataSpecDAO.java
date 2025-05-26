package com.bns.bnsref.dao;

import com.bns.bnsref.Entity.CodeList;
import com.bns.bnsref.Entity.Ref_DataSpec;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface Ref_DataSpecDAO extends JpaRepository<Ref_DataSpec, String>, JpaSpecificationExecutor<Ref_DataSpec> {
    @Query("SELECT r.codeRefDataSpec FROM Ref_DataSpec r ORDER BY r.codeRefDataSpec DESC LIMIT 1")
    Optional<String> findLastRefDataSpecCode(); // Récupérer le dernier codeRefDataSpec

    @EntityGraph(attributePaths = {"codeList"}) // Fetch codeList for Ref_DataSpecDTO
    Page<Ref_DataSpec> findAll(Pageable pageable); // Changed to standard findAll
}

