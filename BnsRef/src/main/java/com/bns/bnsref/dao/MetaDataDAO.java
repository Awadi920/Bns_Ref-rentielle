package com.bns.bnsref.dao;

import com.bns.bnsref.Entity.MetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetaDataDAO extends JpaRepository<MetaData, String> {
    @Query("SELECT m.codeMetaData FROM MetaData m ORDER BY m.codeMetaData DESC LIMIT 1")
    Optional<String> findLastMetaDataCode(); // Récupérer le dernier codeMetaData
}
