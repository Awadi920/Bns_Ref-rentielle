package com.bns.bnsref.DAO;

import com.bns.bnsref.Entity.Ref_DataValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface Ref_DataValueDAO extends JpaRepository<Ref_DataValue, String> {
    @Query("SELECT r.codeRefDataValue FROM Ref_DataValue r ORDER BY r.codeRefDataValue DESC LIMIT 1")
    Optional<String> findLastRefDataValueCode(); // Récupérer le dernier codeRefDataValue
}
