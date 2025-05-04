package com.bns.bnsref.dao;

import com.bns.bnsref.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface CategoryDAO extends JpaRepository<Category, String> {
    @Query("SELECT c.codeCategory FROM Category c ORDER BY c.codeCategory DESC LIMIT 1")
    Optional<String> findLastCategoryCode(); // Récupérer le dernier codeCategory
}
