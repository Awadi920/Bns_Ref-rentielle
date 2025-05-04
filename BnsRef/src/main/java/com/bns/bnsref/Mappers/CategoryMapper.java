package com.bns.bnsref.Mappers;


import com.bns.bnsref.dto.CategoryDTO;
import com.bns.bnsref.Entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDTO dto) {
        return Category.builder()
                .categoryName(dto.getCategoryName())
                .categoryDescription(dto.getCategoryDescription())

                .build();
    }

    public CategoryDTO toDTO(Category entity) {
        return CategoryDTO.builder()
                .codeCategory(entity.getCodeCategory())
                .categoryName(entity.getCategoryName())
                .categoryDescription(entity.getCategoryDescription())

                .build();
    }
}
