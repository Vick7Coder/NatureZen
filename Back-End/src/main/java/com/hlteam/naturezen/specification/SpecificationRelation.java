package com.hlteam.naturezen.specification;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationRelation <T, R>{
    Specification<T> joinEqual(String keyjoin, String key, Object value);
}
