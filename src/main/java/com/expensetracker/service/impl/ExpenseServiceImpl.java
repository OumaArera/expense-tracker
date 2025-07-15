package com.expensetracker.service.impl;

import com.expensetracker.dto.CategoryDTO;
import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.ExpenseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private ExpenseDTO mapToDTO(Expense expense) {
        Category category = expense.getCategory();
        CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getName());

        return new ExpenseDTO(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getDate(),
                // category.getId(),
                categoryDTO,
                expense.getCreatedAt(),
                expense.getUpdatedAt()
        );
    }

    @Override
    public Page<ExpenseDTO> getExpenses(String search, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return expenseRepository
                .findByDescriptionContainingIgnoreCaseAndDateBetween(search, startDate, endDate, pageable)
                .map(this::mapToDTO);
    }

    @Override
    public ExpenseDTO getExpenseById(UUID id) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        return mapToDTO(expense);
    }

    @Override
    public ExpenseDTO createExpense(ExpenseDTO dto) {
        UUID categoryId = dto.getCategory().getId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Expense expense = new Expense();
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        expense.setCategory(category);

        return mapToDTO(expenseRepository.save(expense));
    }

    @Override
    public ExpenseDTO updateExpense(UUID id, ExpenseDTO dto) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        UUID categoryId = dto.getCategory().getId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        expense.setCategory(category);

        return mapToDTO(expenseRepository.save(expense));
    }

    @Override
    public void deleteExpense(UUID id) {
        expenseRepository.deleteById(id);
    }
}