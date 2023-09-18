package com.hlteam.naturezen.service;

import com.hlteam.naturezen.dto.request.CategoryDto;
import com.hlteam.naturezen.entity.Category;

import java.util.List;

public interface CategorySevice {
    List<Category> findAll();
    List<Category> findAllEnabled();
    Category createCategory(CategoryDto categoryDto);
    Category updateCategory(int id, CategoryDto categoryDto);
    void enabledCategory(int id);
    void deleteCategory(int id);
}
