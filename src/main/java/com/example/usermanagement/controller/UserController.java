package com.example.usermanagement.controller;

import com.example.usermanagement.entity.UserDemoEntity;
import com.example.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://myusers-theta.vercel.app/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/demo")
    public String welcome() {
        return "Welcome from secure endpoint";
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Page<UserDemoEntity>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<UserDemoEntity> users = userService.getAllUsersDemo(pageable);
            return ResponseEntity.ok(users);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/list/filter")
    public ResponseEntity<Page<UserDemoEntity>> getAllUsersDemoFilter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UserDemoEntity> users = userService.findUsersDemoFilter(name, email, company, country, role, status, pageable);

        return ResponseEntity.ok(users);
    }

    @GetMapping("/username")
    public String getUsername(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<UserDemoEntity> addUderDemo(@RequestBody UserDemoEntity userDemo) {

        UserDemoEntity userDemoEntity = userService.save(userDemo);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDemoEntity);
    }

    @GetMapping
    public ResponseEntity<UserDemoEntity> getUserDemoById(@RequestParam Integer userId) {
        UserDemoEntity userDemoEntity = userService.findUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userDemoEntity);
    }

    @PostMapping(value = "/edit")
    public ResponseEntity<UserDemoEntity> editUserById(@RequestBody Map<String, Object> requestData) {

        String userIdString = (String) requestData.get("userId");
        userService.edit(requestData);
        UserDemoEntity userDemoEntity = userService.findUserById(Integer.parseInt(userIdString));
        return ResponseEntity.status(HttpStatus.OK).body(userDemoEntity);
    }

    @DeleteMapping(value ="/delete")
    public ResponseEntity<UserDemoEntity> deleteUserById(@RequestParam Integer userId) {
        userService.delete(userId);
        UserDemoEntity userDemoEntity = userService.findUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userDemoEntity);
    }

}
