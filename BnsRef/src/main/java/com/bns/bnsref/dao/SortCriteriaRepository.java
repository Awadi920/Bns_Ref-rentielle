package com.bns.bnsref.dao;

import com.bns.bnsref.Filter.SortCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SortCriteriaRepository extends JpaRepository<SortCriteria, Long> {
    List<SortCriteria> findByEntityName(String entityName);
}
