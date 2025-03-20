package com.bns.bnsref.DAO;

import com.bns.bnsref.Entity.Ref_DataSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface Ref_DataSpecDAO extends JpaRepository<Ref_DataSpec, String> {
    @Query("SELECT r.codeRefDataSpec FROM Ref_DataSpec r ORDER BY r.codeRefDataSpec DESC LIMIT 1")
    Optional<String> findLastRefDataSpecCode(); // Récupérer le dernier codeRefDataSpec

}
