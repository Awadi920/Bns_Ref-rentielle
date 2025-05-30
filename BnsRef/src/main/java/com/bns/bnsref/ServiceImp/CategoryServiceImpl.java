package com.bns.bnsref.ServiceImp;

import com.bns.bnsref.dao.CategoryDAO;
import com.bns.bnsref.dto.CategoryDTO;
import com.bns.bnsref.Entity.Category;

import com.bns.bnsref.Mappers.CategoryMapper;
import com.bns.bnsref.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        // Générer automatiquement le codeCategory
        String lastCodeCategory = categoryDAO.findLastCategoryCode().orElse("CAT000");
        int nextId = Integer.parseInt(lastCodeCategory.replace("CAT", "")) + 1;
        String newCodeCategory = String.format("CAT%03d", nextId); // Format CAT001, CAT002...

        // Convertir le DTO en entité
        Category category = categoryMapper.toEntity(categoryDTO);
        category.setCodeCategory(newCodeCategory);


        // Sauvegarder l'entité
        Category savedCategory = categoryDAO.save(category);

        // Retourner le DTO
        return categoryMapper.toDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(String codeCategory, CategoryDTO categoryDTO) {
        Category category = categoryDAO.findById(codeCategory).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        return categoryMapper.toDTO(categoryDAO.save(category));
    }

    @Override
    public void deleteCategory(String codeCategory) {
        categoryDAO.deleteById(codeCategory);
    }

    @Override
    public CategoryDTO getCategoryById(String codeCategory) {
        return categoryMapper.toDTO(categoryDAO.findById(codeCategory).orElseThrow(() -> new RuntimeException("Category not found")));
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryDAO.findAll().stream().map(categoryMapper::toDTO).collect(Collectors.toList());
    }
}