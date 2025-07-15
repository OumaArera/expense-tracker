package com.expensetracker;

import com.expensetracker.dto.CategoryDTO;
import com.expensetracker.model.Category;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private ExpenseRepository expenseRepository;

    private UUID categoryId;
    private Category category;
    private CategoryDTO categoryDTO;
   

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        categoryId = UUID.randomUUID();
        category = new Category();
        category.setId(categoryId);
        category.setName("Groceries");

        categoryDTO = new CategoryDTO(categoryId, "Groceries");
    }

    @Test
    public void testGetAllCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<CategoryDTO> result = categoryService.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Groceries", result.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testCreateCategory() {
        Category newCategory = new Category("Groceries");
        newCategory.setId(categoryId);

        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        CategoryDTO result = categoryService.createCategory(new CategoryDTO(null, "Groceries"));

        assertNotNull(result.getId());
        assertEquals("Groceries", result.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testUpdateCategory() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryDTO updateDTO = new CategoryDTO(null, "Utilities");
        CategoryDTO result = categoryService.updateCategory(categoryId, updateDTO);

        assertEquals("Utilities", result.getName());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testDeleteCategory() {
        UUID categoryId = UUID.randomUUID();

        // Mock: No expenses exist for this category
        when(expenseRepository.existsByCategoryId(categoryId)).thenReturn(false);
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        // Act
        categoryService.deleteCategory(categoryId);

        // Assert
        verify(categoryRepository).deleteById(categoryId);
    }

    @Test
    public void testUpdateCategory_NotFound() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                categoryService.updateCategory(categoryId, new CategoryDTO(null, "New")));

        assertEquals("Category not found", exception.getMessage());
    }
}
