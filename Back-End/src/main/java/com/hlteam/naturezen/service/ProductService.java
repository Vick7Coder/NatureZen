package com.hlteam.naturezen.service;

import com.hlteam.naturezen.dto.request.CreateProductRequest;
import com.hlteam.naturezen.entity.Product;

import java.util.List;


public interface ProductService {
    
    List<Product> getList();

    List<Product> getListNewst(int number);

    List<Product> getListByPrice();

    List<Product> findRelatedProduct(long id);

    List<Product> getListProductByCategory(long id);

    List<Product> getListByPriceRange(long id,int min, int max);

    List<Product> searchProduct(String keyword);

    Product getProduct(long id);

    Product createProduct(CreateProductRequest request);

    Product updateProduct(long id, CreateProductRequest request);

    void deleteProduct(long id);

}
