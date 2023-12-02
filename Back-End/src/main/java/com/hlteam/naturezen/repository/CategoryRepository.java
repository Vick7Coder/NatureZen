package com.hlteam.naturezen.repository;

import com.hlteam.naturezen.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {
    @Query("select c from Category c where c.enabled = true ")
    List<Category> findAllByEnabled();

    @Query("select c from Category c where c.name = ?1")
    Optional<Category> findByName(String name);
    Optional<Category> findById(int id);


}
