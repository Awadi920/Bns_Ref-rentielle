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

    @Query("SELECT c.codeList FROM CodeList c ORDER BY c.codeList DESC LIMIT 1")
    Optional<String> findLastCodeList();

    @EntityGraph(attributePaths = {"sourceRelations", "targetRelations", "refData", "refDataSpec", "translations", "domain", "category", "producer"})
    Optional<CodeList> findById(String codeListCode);

    @EntityGraph(attributePaths = {"sourceRelations", "targetRelations", "refData", "refDataSpec", "translations", "domain", "category", "producer"})
    List<CodeList> findAll();

    @Query("SELECT DISTINCT cl FROM CodeList cl " +
            "LEFT JOIN FETCH cl.domain " +
            "LEFT JOIN FETCH cl.category " +
            "LEFT JOIN FETCH cl.producer " +
            "LEFT JOIN FETCH cl.refData " +
            "LEFT JOIN FETCH cl.refDataSpec " +
            "LEFT JOIN FETCH cl.translations " +
            "LEFT JOIN FETCH cl.metaData")
    List<CodeList> findAllWithRelations();

//    @Query("SELECT DISTINCT cl FROM CodeList cl " +
//            "LEFT JOIN FETCH cl.sourceRelations sr " +
//            "LEFT JOIN FETCH cl.targetRelations tr")
//    List<CodeList> findAllWithRelations();



//    @EntityGraph(attributePaths = {"sourceRelations", "targetRelations"})
//    Optional<CodeList> findById(String codeListCode);

//    @Query("SELECT DISTINCT cl FROM CodeList cl " +
//            "LEFT JOIN FETCH cl.domain " +
//            "LEFT JOIN FETCH cl.category " +
//            "LEFT JOIN FETCH cl.producer " +
//            "LEFT JOIN FETCH cl.refData rd " +
//            "LEFT JOIN FETCH rd.refDataValues rdv " +
//            "LEFT JOIN FETCH rdv.childValues cv " + // Ajout pour charger les enfants
//            "LEFT JOIN FETCH cl.refDataSpec rs " +
//            "LEFT JOIN FETCH rs.refDataSpecValues rsv")
//    List<CodeList> findAllWithDetails();
//
//    @Query("SELECT DISTINCT cl FROM CodeList cl " +
//            "LEFT JOIN FETCH cl.refData rd " +
//            "LEFT JOIN FETCH rd.refDataValues rdv " +
//            "LEFT JOIN FETCH cl.refDataSpec rs " +
//            "LEFT JOIN FETCH rs.refDataSpecValues rsv " +
//            "WHERE cl.codeList = :idOrLabel OR cl.labelList = :idOrLabel")
//    Optional<CodeList> findByCodeListOrLabelListWithDetails(@Param("idOrLabel") String idOrLabel);
//

//        @Query("SELECT DISTINCT cl FROM CodeList cl " +
//                "LEFT JOIN FETCH cl.translations " +
//                "LEFT JOIN FETCH cl.domain " +
//                "LEFT JOIN FETCH cl.category " +
//                "LEFT JOIN FETCH cl.producer " +
//                "LEFT JOIN FETCH cl.refData rd " +
//                "LEFT JOIN FETCH rd.translations " +
//                "LEFT JOIN FETCH rd.refDataValues rdv " +
//                "LEFT JOIN FETCH rdv.translations " +
//                "LEFT JOIN FETCH rdv.childValues " +
//                "LEFT JOIN FETCH cl.refDataSpec rds " +
//                "LEFT JOIN FETCH rds.translations " +
//                "LEFT JOIN FETCH rds.refDataSpecValues rdsv " +
//                "LEFT JOIN FETCH rdsv.translations " +
//                "LEFT JOIN FETCH cl.sourceRelations sr " +
//                "LEFT JOIN FETCH sr.codeListCible " +
//                "LEFT JOIN FETCH cl.targetRelations tr " +
//                "LEFT JOIN FETCH tr.codeListSource")
//        List<CodeList> findAllWithDetails();
//
//        @Query("SELECT DISTINCT cl FROM CodeList cl " +
//                "LEFT JOIN FETCH cl.translations " +
//                "LEFT JOIN FETCH cl.domain " +
//                "LEFT JOIN FETCH cl.category " +
//                "LEFT JOIN FETCH cl.producer " +
//                "LEFT JOIN FETCH cl.refData rd " +
//                "LEFT JOIN FETCH rd.translations " +
//                "LEFT JOIN FETCH rd.refDataValues rdv " +
//                "LEFT JOIN FETCH rdv.translations " +
//                "LEFT JOIN FETCH rdv.childValues " +
//                "LEFT JOIN FETCH cl.refDataSpec rds " +
//                "LEFT JOIN FETCH rds.translations " +
//                "LEFT JOIN FETCH rds.refDataSpecValues rdsv " +
//                "LEFT JOIN FETCH rdsv.translations " +
//                "LEFT JOIN FETCH cl.sourceRelations sr " +
//                "LEFT JOIN FETCH sr.codeListCible " +
//                "LEFT JOIN FETCH cl.targetRelations tr " +
//                "LEFT JOIN FETCH tr.codeListSource " +
//                "WHERE cl.codeList = :idOrLabel OR cl.labelList = :idOrLabel")
//        Optional<CodeList> findByCodeListOrLabelListWithDetails(String idOrLabel);
//
//
//
//
//    @Query("SELECT MAX(cl.codeList) FROM CodeList cl")
//    Optional<String> findLastCodeListCode();
//
//    @Query("SELECT cl FROM CodeList cl " +
//            "LEFT JOIN FETCH cl.refData rd " +
//            "LEFT JOIN FETCH rd.refDataValues rdv " +
//            "LEFT JOIN FETCH rdv.childValues " +
//            "LEFT JOIN FETCH rdv.translations " +
//            "LEFT JOIN FETCH cl.refDataSpec rds " +
//            "LEFT JOIN FETCH rds.refDataSpecValues rdsv " +
//            "LEFT JOIN FETCH rdsv.translations " +
//            "LEFT JOIN FETCH cl.sourceRelations " +
//            "LEFT JOIN FETCH cl.targetRelations " +
//            "LEFT JOIN FETCH cl.translations")
//    List<CodeList> findAllWithRelations();
}
