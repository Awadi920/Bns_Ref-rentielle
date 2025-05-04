package com.bns.bnsref.dao;

import com.bns.bnsref.Entity.TypeRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRelationDAO extends JpaRepository<TypeRelation, Long> {
    Optional<TypeRelation> findByCode(String code);
}
