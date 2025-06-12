package com.bns.bnsref.dao;

import com.bns.bnsref.Entity.Ref_DataValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Ref_DataValueDAO extends JpaRepository<Ref_DataValue, String> {
    @Query("SELECT r.codeRefDataValue FROM Ref_DataValue r ORDER BY r.codeRefDataValue DESC LIMIT 1")
    Optional<String> findLastRefDataValueCode(); // Récupérer le dernier codeRefDataValue

    Optional<Ref_DataValue> findById(String codeRefDataValue);

    @Query("SELECT rdv FROM Ref_DataValue rdv WHERE rdv.refData.codeList.codeList = :codeListId")
    List<Ref_DataValue> findByCodeListId(@Param("codeListId") String codeListId);


}

