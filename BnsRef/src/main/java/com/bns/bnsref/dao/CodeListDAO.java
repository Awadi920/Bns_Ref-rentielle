package com.bns.bnsref.dao;

import com.bns.bnsref.Entity.CodeList;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeListDAO extends JpaRepository<CodeList, String> , JpaSpecificationExecutor<CodeList> {

//    @Query("SELECT c.codeList FROM CodeList c ORDER BY c.codeList DESC LIMIT 1")
//    Optional<String> findLastCodeList();
//
//    @EntityGraph(attributePaths = {"sourceRelations", "targetRelations", "refData", "refDataSpec", "translations", "domain", "category", "producer"})
//    Optional<CodeList> findById(String codeListCode);
//
//    @EntityGraph(attributePaths = {"sourceRelations", "targetRelations", "refData", "refDataSpec", "translations", "domain", "category", "producer"})
//    List<CodeList> findAll();
//
@Query("SELECT DISTINCT cl FROM CodeList cl " +
        "LEFT JOIN FETCH cl.domain " +
        "LEFT JOIN FETCH cl.category " +
        "LEFT JOIN FETCH cl.producer " +
        "LEFT JOIN FETCH cl.refData rd " +
        "LEFT JOIN FETCH rd.refDataValues " + // Add fetch for refDataValues
        "LEFT JOIN FETCH cl.refDataSpec " +
        "LEFT JOIN FETCH cl.translations " +
        "LEFT JOIN FETCH cl.metaData")
List<CodeList> findAllWithRelations();

    @EntityGraph(attributePaths = {
            "refData",
            "refData.refDataValues", // Add refData.refDataValues to fetch values
            "refDataSpec",
            "translations",
            "domain",
            "category",
            "producer"
    })
    List<CodeList> findAll();

    @Query("SELECT c.codeList FROM CodeList c ORDER BY c.codeList DESC LIMIT 1")
    Optional<String> findLastCodeList();

    @EntityGraph(attributePaths = {
            "sourceRelations",
            "targetRelations",
            "refData",
            "refData.refDataValues", // Add refData.refDataValues to fetch values
            "refDataSpec",
            "translations",
            "domain",
            "category",
            "producer"
    })
    Optional<CodeList> findById(String codeListCode);
}
