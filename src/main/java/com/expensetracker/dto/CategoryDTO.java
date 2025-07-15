package com.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public class CategoryDTO {
    private UUID id;

    @NotBlank(message = "Category name must not be blank")
    @Size(max = 100, message = "Category name must not exceed 100 characters")
    private String name;

    public CategoryDTO() {}

    public CategoryDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
