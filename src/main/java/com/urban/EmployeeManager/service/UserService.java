package com.urban.EmployeeManager.service;

import com.urban.EmployeeManager.model.User;
import com.urban.EmployeeManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getListUser(){
        System.out.println("cc");
        return userRepository.findAll() ;

    }

    public User addUser(User user){
        return userRepository.save(user);
    }



}
