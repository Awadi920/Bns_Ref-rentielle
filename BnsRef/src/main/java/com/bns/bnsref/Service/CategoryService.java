package com.bns.bnsref.Service;

import com.bns.bnsref.dto.CategoryDTO;

import java.util.*;

public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(String codeCategory, CategoryDTO categoryDTO);
    void deleteCategory(String codeCategory);
    CategoryDTO getCategoryById(String codeCategory);
    List<CategoryDTO> getAllCategories();
}
