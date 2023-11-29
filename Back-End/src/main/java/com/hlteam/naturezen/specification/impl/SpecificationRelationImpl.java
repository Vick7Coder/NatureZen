package com.hlteam.naturezen.specification.impl;

import com.hlteam.naturezen.specification.SpecificationRelation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SpecificationRelationImpl <T,R> implements SpecificationRelation<T, R> {
    @Override
    public Specification<T> joinEqual(String keyjoin, String key, Object value) {
        return null;
    }
}
