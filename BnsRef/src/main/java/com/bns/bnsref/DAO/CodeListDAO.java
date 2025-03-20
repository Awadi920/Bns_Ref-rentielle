package com.bns.bnsref.DAO;

import com.bns.bnsref.Entity.CodeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeListDAO extends JpaRepository<CodeList, String> {

    @Query("SELECT DISTINCT cl FROM CodeList cl " +
            "LEFT JOIN FETCH cl.domain " +
            "LEFT JOIN FETCH cl.category " +
            "LEFT JOIN FETCH cl.producer " +
            "LEFT JOIN FETCH cl.refData rd " +
            "LEFT JOIN FETCH rd.refDataValues rdv " +
            "LEFT JOIN FETCH cl.refDataSpec rs " +
            "LEFT JOIN FETCH rs.refDataSpecValues rsv")
    List<CodeList> findAllWithDetails();

    @Query("SELECT cl FROM CodeList cl")
    List<CodeList> findAllWithoutDetails();

    @Query("SELECT DISTINCT cl FROM CodeList cl " +
            "LEFT JOIN FETCH cl.refData rd " +
            "LEFT JOIN FETCH rd.refDataValues rdv " +
            "LEFT JOIN FETCH cl.refDataSpec rs " +
            "LEFT JOIN FETCH rs.refDataSpecValues rsv " +
            "WHERE cl.codeList = :idOrLabel OR cl.labelList = :idOrLabel")
    Optional<CodeList> findByCodeListOrLabelListWithDetails(@Param("idOrLabel") String idOrLabel);


    @Query("SELECT c.codeList FROM CodeList c ORDER BY c.codeList DESC LIMIT 1")
    Optional<String> findLastCodeList();

}
