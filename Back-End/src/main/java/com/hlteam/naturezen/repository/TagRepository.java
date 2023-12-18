package com.hlteam.naturezen.repository;

import com.hlteam.naturezen.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    
}
