package com.emeritus.cms.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emeritus.cms.userservice.model.User;
import com.emeritus.cms.userservice.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveUser(User user) { 
        return userRepository.save(user);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    // Other service methods
}
