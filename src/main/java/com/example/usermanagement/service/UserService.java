package com.example.usermanagement.service;

import com.example.usermanagement.entity.UserDemoEntity;
import com.example.usermanagement.entity.UserEntity;
import com.example.usermanagement.repository.UserDemoRepository;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.specifications.UserDemoSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDemoRepository userDemoRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<UserDemoEntity> getAllUsersDemo(Pageable pageable) {
        return userDemoRepository.findAll(pageable);
    }


    public Page<UserDemoEntity> findUsersDemoFilter1(String name, String email, String company, String country, String role, String status, Pageable pageable) {
        return userDemoRepository.findByName(name, pageable);
    }
/*
    public Page<UserDemoEntity> findUsersDemoFilter(
            String name, String email, String company, String country, String role, String status, Pageable pageable) {
        return userDemoRepository.findUsersWithFilters(name, email, company, country, role, status, pageable);
    }

 */

    public Page<UserDemoEntity> findUsersDemoFilter(
            String name, String email, String company, String country, String role, String status, Pageable pageable) {
        Specification<UserDemoEntity> spec = Specification.where(null);

        if (name != null && !name.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (email != null && !email.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }
        if (company != null && !company.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("company")), "%" + company.toLowerCase() + "%"));
        }
        if (country != null && !country.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("country")), "%" + country.toLowerCase() + "%"));
        }
        if (role != null && !role.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("role")), "%" + role.toLowerCase() + "%"));
        }
        if (status != null && !status.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(cb.lower(root.get("status")), status.toLowerCase()));
        }
        return userDemoRepository.findAll(spec, pageable);
    }

    public String findUserByEmail(String email) {
        return userRepository.findFirstName(email);
    }

    public UserDemoEntity save(UserDemoEntity userDemo) {
        return userDemoRepository.save(userDemo);
    }

    public UserDemoEntity findUserById(Integer userId) {
        return userDemoRepository.findById(userId).orElse(null);
    }

    public void edit(Map<String, Object> requestData) {
        String userIdString = (String) requestData.get("userId");
        String name = (String) requestData.get("name");
        String ageString = (String) requestData.get("age");
        String email = (String) requestData.get("email");
        String country = (String) requestData.get("country");
        String cellphone = (String) requestData.get("cellphone");
        String company = (String) requestData.get("company");
        String salaryString = (String) requestData.get("salary");
        BigDecimal salary = new BigDecimal(salaryString);
        String role = (String) requestData.get("role");
        String status = (String) requestData.get("status");
        userDemoRepository.editUserDemo(Integer.parseInt(userIdString), name, Integer.parseInt(ageString), email, country, cellphone, company, salary, role, status);
    }

    public void delete(Integer userId) {
        userDemoRepository.deleteById(userId);
    }
}
