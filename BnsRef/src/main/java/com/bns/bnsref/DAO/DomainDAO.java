package com.bns.bnsref.DAO;

import com.bns.bnsref.Entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface DomainDAO extends JpaRepository<Domain, String> {
    @Query("SELECT d.codeDomain FROM Domain d ORDER BY d.codeDomain DESC LIMIT 1")
    Optional<String> findLastDomainCode(); // Récupérer le dernier codeDomain
}

