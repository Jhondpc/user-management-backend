package com.example.usermanagement.repository;

import com.example.usermanagement.entity.UserDemoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UserDemoRepository extends JpaRepository<UserDemoEntity, Integer>, JpaSpecificationExecutor<UserDemoEntity> {

    @Query(nativeQuery = true, value = "SELECT * FROM users_demo WHERE LOWER(name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<UserDemoEntity> findByName(String name, Pageable pageable);
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users_demo SET name = ?2, age=?3, email=?4, country=?5, cellphone=?6, company=?7, salary=?8, role=?9, status=?10 WHERE id = ?1")
    void editUserDemo(Integer userId, String name, Integer age, String email, String country, String cellphone, String company, BigDecimal salary, String role, String status);
}
