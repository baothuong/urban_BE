package com.urban.EmployeeManager.controller;

import com.urban.EmployeeManager.model.User;
import com.urban.EmployeeManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> getListUser(){
        return ResponseEntity.ofNullable(userService.getListUser());
    }
    @PostMapping
    public User addUser(@RequestBody User data){
        System.out.println(userService.addUser(data));
        return null;
    }
}
