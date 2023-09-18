package com.hlteam.naturezen.controller;

import com.hlteam.naturezen.dto.request.CategoryDto;
import com.hlteam.naturezen.dto.response.MessageResp;
import com.hlteam.naturezen.entity.Category;
import com.hlteam.naturezen.service.CategorySevice;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {
    @Autowired
    private CategorySevice categorySevice;
    @GetMapping("/")
    @Operation(summary = "Lấy tất cả danh mục")
    public ResponseEntity<?> getCategoryList(){
        List<Category> categories = categorySevice.findAll();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/enabled")
    @Operation(summary = "Lấy tất cả danh mục đang kích hoạt")
    public ResponseEntity<?> getCategoryListEnabled(){
        List<Category> categories = categorySevice.findAllEnabled();
        return ResponseEntity.ok(categories);
    }
    @PostMapping("/create")
    @Operation(summary = "Thêm category mới")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        Category category = categorySevice.createCategory(categoryDto);
        return ResponseEntity.ok(category);
    }
    @PutMapping("/update/{id}")
    @Operation(summary = "Cập nhật category theo id")
    public ResponseEntity<?> updateCategory(@PathVariable int id, @Valid @RequestBody CategoryDto categoryDto){
        Category category = categorySevice.updateCategory(id, categoryDto);
        return ResponseEntity.ok(category);
    }
    @PutMapping("/enabled/{id}")
    @Operation(summary = "Kích hoạt hoặc huỷ ki1ch hoạt category theo id")
    public  ResponseEntity<?> enableCategory(@PathVariable int id){
        categorySevice.enabledCategory(id);
        return ResponseEntity.ok(new MessageResp("Update Successful!"));
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Xoá category theo id")
    public ResponseEntity<?> deleteCategory(@PathVariable int id){
        categorySevice.deleteCategory(id);
        return ResponseEntity.ok(new MessageResp("Deleted!"));
    }
}
