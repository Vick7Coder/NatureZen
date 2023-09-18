package com.hlteam.naturezen.service.impl;

import com.hlteam.naturezen.dto.request.CategoryDto;
import com.hlteam.naturezen.entity.Category;
import com.hlteam.naturezen.exception.NotFoundException;
import com.hlteam.naturezen.repository.CategoryRepository;
import com.hlteam.naturezen.service.CategorySevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategorySevice {
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        List<Category> list = categoryRepository.findAll(Sort.by("id").descending());
        return list;
    }

    @Override
    public List<Category> findAllEnabled() {
        List<Category> list = categoryRepository.findAllByEnabled();
        return list;
    }

    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setEnabled(false);
        categoryRepository.save(category);
        return category;
    }

    @Override
    public Category updateCategory(int id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new NotFoundException("Not Found: "+id));
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return category;
    }

    @Override
    public void enabledCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new NotFoundException("Not Found: "+id));
        category.setEnabled(!category.isEnabled());
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new NotFoundException("Not Found: "+id));
        categoryRepository.delete(category);
    }
}
