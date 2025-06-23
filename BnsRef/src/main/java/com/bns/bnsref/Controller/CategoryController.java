package com.bns.bnsref.Controller;

import com.bns.bnsref.dto.CategoryDTO;
import com.bns.bnsref.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.addCategory(categoryDTO));
    }

    @PutMapping("/update/{codeCategory}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable String codeCategory, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(codeCategory, categoryDTO));
    }

    @DeleteMapping("/delete/{codeCategory}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String codeCategory) {
        categoryService.deleteCategory(codeCategory);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{codeCategory}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable String codeCategory) {
        return ResponseEntity.ok(categoryService.getCategoryById(codeCategory));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
