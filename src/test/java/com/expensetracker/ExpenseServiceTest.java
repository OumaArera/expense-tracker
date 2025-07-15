// ExpenseServiceTest.java

package com.expensetracker;

import com.expensetracker.dto.CategoryDTO;
import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.impl.ExpenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private UUID expenseId;
    private UUID categoryId;
    private Category category;
    private Expense expense;
    private ExpenseDTO expenseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        expenseId = UUID.randomUUID();
        categoryId = UUID.randomUUID();

        category = new Category();
        category.setId(categoryId);
        category.setName("Food");

        expense = new Expense();
        expense.setId(expenseId);
        expense.setDescription("Lunch");
        expense.setAmount(100.0);
        expense.setDate(LocalDate.now());
        expense.setCategory(category);

        CategoryDTO categoryDTO = new CategoryDTO(categoryId, "Food");
        expenseDTO = new ExpenseDTO(expenseId, "Lunch", 100.0, LocalDate.now(), categoryDTO, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void testGetExpenseById() {
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expense));

        ExpenseDTO result = expenseService.getExpenseById(expenseId);
        assertNotNull(result);
        assertEquals("Lunch", result.getDescription());
    }

    @Test
    void testCreateExpense() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        ExpenseDTO result = expenseService.createExpense(expenseDTO);
        assertNotNull(result);
        assertEquals("Lunch", result.getDescription());
        verify(expenseRepository).save(any(Expense.class));
    }

    @Test
    void testUpdateExpense() {
        when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(expense));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        ExpenseDTO result = expenseService.updateExpense(expenseId, expenseDTO);
        assertNotNull(result);
        assertEquals("Lunch", result.getDescription());
    }

    @Test
    void testDeleteExpense() {
        doNothing().when(expenseRepository).deleteById(expenseId);
        expenseService.deleteExpense(expenseId);
        verify(expenseRepository).deleteById(expenseId);
    }

    @Test
    void testGetExpensesWithSearchAndPagination() {
        PageRequest pageable = PageRequest.of(0, 10);
        when(expenseRepository.findByDescriptionContainingIgnoreCaseAndDateBetween(anyString(), any(), any(), eq(pageable)))
                .thenReturn(new PageImpl<>(Collections.singletonList(expense)));

        Page<ExpenseDTO> result = expenseService.getExpenses("Lunch", LocalDate.now().minusDays(1), LocalDate.now().plusDays(1), pageable);
        assertEquals(1, result.getContent().size());
        assertEquals("Lunch", result.getContent().get(0).getDescription());
    }
}
