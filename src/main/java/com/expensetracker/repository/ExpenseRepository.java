package com.expensetracker.repository;

import com.expensetracker.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    Page<Expense> findByDescriptionContainingIgnoreCaseAndDateBetween(
            String description, LocalDate startDate, LocalDate endDate, Pageable pageable);
    boolean existsByCategoryId(UUID categoryId);
}