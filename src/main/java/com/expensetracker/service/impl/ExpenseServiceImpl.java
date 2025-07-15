package com.expensetracker.service.impl;

import com.expensetracker.dto.CategoryDTO;
import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.dto.ExpenseUpdateDTO;
import com.expensetracker.exception.ResourceNotFoundException;
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
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        return mapToDTO(expense);
    }

    @Override
    public ExpenseDTO createExpense(ExpenseDTO dto) {
        if (dto.getCategory() == null) {
            throw new ResourceNotFoundException("Category is required");
        }

        UUID categoryId = dto.getCategory().getId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        Expense expense = new Expense();
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());
        expense.setCategory(category);

        return mapToDTO(expenseRepository.save(expense));
    }

    @Override
    public ExpenseDTO updateExpense(UUID id, ExpenseUpdateDTO dto) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        if (dto.getDescription() != null) {
            expense.setDescription(dto.getDescription());
        }

        if (dto.getAmount() != null) {
            expense.setAmount(dto.getAmount());
        }

        if (dto.getDate() != null) {
            expense.setDate(dto.getDate());
        }

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));
            expense.setCategory(category);
        }

        return mapToDTO(expenseRepository.save(expense));
    }

    @Override
    public void deleteExpense(UUID id) {
        expenseRepository.deleteById(id);
    }
}