package com.hlteam.naturezen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hlteam.naturezen.Filter.Category.CategoryFilter;
import com.hlteam.naturezen.dto.request.CategoryDto;
import com.hlteam.naturezen.dto.response.MessageResp;
import com.hlteam.naturezen.entity.Category;
import com.hlteam.naturezen.entity.ERole;
import com.hlteam.naturezen.entity.Role;
import com.hlteam.naturezen.entity.User;
import com.hlteam.naturezen.exception.BadRequestException;
import com.hlteam.naturezen.security.service.UserDetailsImpl;
import com.hlteam.naturezen.service.CategorySevice;
import com.hlteam.naturezen.service.payload.PagingRequest;
import com.hlteam.naturezen.service.payload.Sorter;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/category")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {
    @Autowired
    private CategorySevice categorySevice;

    @Autowired
    private ObjectMapper objectMapper;

    private Authentication authentication;

    @GetMapping("")
    @Operation(summary = "Lấy tất cả danh mục")
    public ResponseEntity<?> getCategoryList(@RequestParam(name = "filter") String filter,
                                             @RequestParam(name = "range") String range,
                                             @RequestParam(name = "sort") String sort,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication instanceof AnonymousAuthenticationToken){
                throw new BadRequestException("The request has failed." +
                        " Please send the JWT of the user who needs to view the wishlist along with the request.");
            }
            List<String> _sort = objectMapper.readValue(sort, ArrayList.class);
            List<Integer> _range = objectMapper.readValue(range, ArrayList.class);
            CategoryFilter _filter = objectMapper.readValue(filter, CategoryFilter.class);
            StringBuilder contentRange = new StringBuilder("category ");
            contentRange.append(_range.get(0))
                    .append("-")
                    .append(_range.get(1))
                    .append("/")
                    .append(categorySevice
                            .count(_filter));
            response.setHeader("Content-Range", contentRange.toString());
            PagingRequest pagingRequest = new PagingRequest();
            pagingRequest.setLimit(_range.get(1) - _range.get(0) + 1);
            pagingRequest.setPage((int) Math.ceil(_range.get(0) / pagingRequest.getLimit() + 1));
            Sorter sorter = new Sorter();
            sorter.setName(_sort.get(0));
            sorter.setBy(_sort.get(1));
            pagingRequest.setSorter(sorter);
            List<Category> categories = categorySevice.findAll(_filter, pagingRequest);
            if (categories == null){
                categories = new ArrayList<>();
            }
            return ResponseEntity.ok(categories);
        }catch (JsonProcessingException exception){
            throw new RuntimeException(exception);
        }
    }

    @GetMapping("/enabled")
    @Operation(summary = "Lấy tất cả danh mục đang kích hoạt")
    public ResponseEntity<?> getCategoryListEnabled() {
        List<Category> categories = categorySevice.findAllEnabled();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/")
    @Operation(summary = "Thêm category mới")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        if (user != null && user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN))) {
            Category category = categorySevice.createCategory(categoryDto);
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.badRequest().body(new MessageResp("Not contain role admin!"));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Cập nhật category theo id")
    public ResponseEntity<?> updateCategory(@PathVariable int id, @Valid @RequestBody CategoryDto categoryDto) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        if (user != null && user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN))) {
            Category category = categorySevice.updateCategory(id, categoryDto);
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.badRequest().body(new MessageResp("Not contain role admin!"));
    }

    @PutMapping("/enabled/{id}")
    @Operation(summary = "Kích hoạt hoặc huỷ kích hoạt category theo id")
    public ResponseEntity<?> enableCategory(@PathVariable int id) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        if (user != null && user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN))) {
            categorySevice.enabledCategory(id);
            return ResponseEntity.ok(new MessageResp("Update Successful!"));
        }
        return ResponseEntity.badRequest().body(new MessageResp("Not contain role admin!"));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Xoá category theo id")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new BadRequestException("The request has failed." +
                    " Please send the JWT of the user who needs to view the wishlist along with the request.");
        }
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        if (user != null && user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ROLE_ADMIN))) {
            categorySevice.deleteCategory(id);
            return ResponseEntity.ok(new MessageResp("Deleted!"));
        }
        return ResponseEntity.badRequest().body(new MessageResp("Not contain role admin!"));
    }
}
