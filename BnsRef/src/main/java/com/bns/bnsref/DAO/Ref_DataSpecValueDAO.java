package com.bns.bnsref.DAO;

import com.bns.bnsref.Entity.Ref_DataSpecValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface Ref_DataSpecValueDAO extends JpaRepository<Ref_DataSpecValue, String> {
    @Query("SELECT r.codeRefDataSpecValue FROM Ref_DataSpecValue r ORDER BY r.codeRefDataSpecValue DESC LIMIT 1")
    Optional<String> findLastRefDataSpecValueCode(); // Récupérer le dernier codeRefDataSpecValue
}
