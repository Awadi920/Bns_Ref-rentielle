package com.bns.bnsref.DAO;

import com.bns.bnsref.Entity.Ref_Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface Ref_DataDAO extends JpaRepository<Ref_Data, String> {
    @Query("SELECT r.codeRefData FROM Ref_Data r ORDER BY r.codeRefData DESC LIMIT 1")
    Optional<String> findLastRefDataCode(); // Récupérer le dernier codeRefData
}
