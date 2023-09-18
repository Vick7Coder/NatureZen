package com.hlteam.naturezen.repository;

import com.hlteam.naturezen.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select c from Category c where c.enabled = true ")
    List<Category> findAllByEnabled();
}
