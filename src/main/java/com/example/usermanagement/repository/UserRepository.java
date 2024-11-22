package com.example.usermanagement.repository;

import com.example.usermanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT firstname FROM user WHERE username = ?1")
    String findFirstName(String username);
}
