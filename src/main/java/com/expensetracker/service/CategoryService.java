package com.expensetracker.service;

import com.expensetracker.dto.CategoryDTO;
import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    CategoryDTO createCategory(CategoryDTO dto);
    CategoryDTO updateCategory(UUID id, CategoryDTO dto);
    void deleteCategory(UUID id);
}
