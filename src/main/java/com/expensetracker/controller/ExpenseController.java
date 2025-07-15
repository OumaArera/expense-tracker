package com.expensetracker.controller;

import com.expensetracker.dto.ExpenseDTO;
import com.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public Page<ExpenseDTO> getExpenses(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "2025-01-01") LocalDate startDate,
            @RequestParam(defaultValue = "2100-01-01") LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return expenseService.getExpenses(search, startDate, endDate, pageable);
    }

    @GetMapping("/{id}")
    public ExpenseDTO getExpenseById(@PathVariable UUID id) {
        return expenseService.getExpenseById(id);
    }

    @PostMapping
    public ExpenseDTO createExpense(@RequestBody ExpenseDTO dto) {
        return expenseService.createExpense(dto);
    }

    @PutMapping("/{id}")
    public ExpenseDTO updateExpense(@PathVariable UUID id, @RequestBody ExpenseDTO dto) {
        return expenseService.updateExpense(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable UUID id) {
        expenseService.deleteExpense(id);
    }
}