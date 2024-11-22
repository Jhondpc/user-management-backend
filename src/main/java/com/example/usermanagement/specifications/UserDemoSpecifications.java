package com.example.usermanagement.specifications;

import com.example.usermanagement.entity.UserDemoEntity;
import org.springframework.data.jpa.domain.Specification;

public class UserDemoSpecifications {

    public static Specification<UserDemoEntity> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<UserDemoEntity> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }
}
