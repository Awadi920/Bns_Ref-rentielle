package com.bns.bnsref.DAO;

import com.bns.bnsref.Entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProducerDAO extends JpaRepository<Producer, String> {
    @Query("SELECT p.codeProd FROM Producer p ORDER BY p.codeProd DESC LIMIT 1")
    Optional<String> findLastProducerCode(); // Récupérer le dernier codeProd
}
