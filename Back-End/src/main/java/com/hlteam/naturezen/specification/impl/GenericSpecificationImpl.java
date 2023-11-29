package com.hlteam.naturezen.specification.impl;

import com.hlteam.naturezen.specification.GenericSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


@Component
public class GenericSpecificationImpl <T> implements GenericSpecification<T> {
    @Override
    public Specification<T> like(String key, String value) {
        return null;
    }

    @Override
    public Specification<T> equals(String key, Object value) {
        return null;
    }

    @Override
    public Specification<T> equalIgnoreCase(String key, String value) {
        return null;
    }
}
