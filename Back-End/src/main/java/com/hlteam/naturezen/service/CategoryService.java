package com.hlteam.naturezen.service;

import com.hlteam.naturezen.dto.request.CreateCategoryRequest;
import com.hlteam.naturezen.entity.Category;

import java.util.List;


public interface CategoryService {
    List<Category> findAll();

    List<Category> getListEnabled();

    Category createCategory(CreateCategoryRequest request);

    Category updateCategory(long id,CreateCategoryRequest request);

    void enableCategory(long id);

    void deleteCategory(long id);
}
