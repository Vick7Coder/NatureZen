package com.hlteam.naturezen.repository;

import java.util.List;

import com.hlteam.naturezen.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    
    @Query("Select c from Category c where c.enable = true")
    List<Category> findALLByEnabled();
}
