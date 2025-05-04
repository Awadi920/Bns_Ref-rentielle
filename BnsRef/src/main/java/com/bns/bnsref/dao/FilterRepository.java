package com.bns.bnsref.dao;

import com.bns.bnsref.Filter.Filter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilterRepository extends JpaRepository<Filter, Long> {
    List<Filter> findByEntityName(String entityName);
}


