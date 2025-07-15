package com.expensetracker.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ExpenseDTO {

    private UUID id;
    private String description;
    private Double amount;
    private LocalDate date;
    // private UUID categoryId;
    private CategoryDTO category;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ExpenseDTO() {}

    public ExpenseDTO(UUID id, String description, Double amount, LocalDate date,
                  CategoryDTO category, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters...
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    // public UUID getCategoryId() { return categoryId; }
    // public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }

    public CategoryDTO getCategory() { return category; }
    public void setCategory(CategoryDTO category) { this.category = category; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
