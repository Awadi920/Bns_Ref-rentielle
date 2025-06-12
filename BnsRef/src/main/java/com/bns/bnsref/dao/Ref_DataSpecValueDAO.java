package com.bns.bnsref.dao;

import com.bns.bnsref.Entity.Ref_DataSpecValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Ref_DataSpecValueDAO extends JpaRepository<Ref_DataSpecValue, String> {
    @Query("SELECT r.codeRefDataSpecValue FROM Ref_DataSpecValue r ORDER BY r.codeRefDataSpecValue DESC LIMIT 1")
    Optional<String> findLastRefDataSpecValueCode(); // Récupérer le dernier codeRefDataSpecValue

    Optional<Ref_DataSpecValue> findById(String codeRefDataSpecValue);

    @Query("SELECT rdsv FROM Ref_DataSpecValue rdsv WHERE rdsv.refDataSpec.codeList.codeList = :codeListId")
    List<Ref_DataSpecValue> findByCodeListId(@Param("codeListId") String codeListId);



}
