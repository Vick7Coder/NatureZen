package com.hlteam.naturezen.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    @NotNull(message = "Category name is empty")
    @NotEmpty(message = "Category name is empty")
    @Size(min=2,message="Category name must have a minimum length of 2!")
    private String name;

}
