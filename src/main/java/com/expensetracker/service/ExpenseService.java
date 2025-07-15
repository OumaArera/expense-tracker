package com.expensetracker.service;

import com.expensetracker.dto.ExpenseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface ExpenseService {
    Page<ExpenseDTO> getExpenses(String search, LocalDate startDate, LocalDate endDate, Pageable pageable);
    ExpenseDTO getExpenseById(UUID id);
    ExpenseDTO createExpense(ExpenseDTO dto);
    ExpenseDTO updateExpense(UUID id, ExpenseDTO dto);
    void deleteExpense(UUID id);
}
