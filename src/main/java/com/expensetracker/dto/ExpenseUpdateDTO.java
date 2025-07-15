package com.expensetracker.dto;

import java.time.LocalDate;
import java.util.UUID;

public class ExpenseUpdateDTO {

    private String description;
    private Double amount;
    private LocalDate date;
    private UUID categoryId;

    public ExpenseUpdateDTO() {}

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public UUID getCategoryId() { return categoryId; }
    public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }
}
