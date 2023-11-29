package com.hlteam.naturezen.specification;

import org.springframework.data.jpa.domain.Specification;

public interface GenericSpecification<T> {
    Specification<T> like(String key, String value);
    Specification<T> equals(String key, Object value);
    Specification<T> equalIgnoreCase(String key, String value);
}
