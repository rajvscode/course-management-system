package com.emeritus.cms.userservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emeritus.cms.userservice.model.User;
import com.emeritus.cms.userservice.repository.UserRepository;
import com.emeritus.cms.userservice.security.EmeritusUserDetails;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

     @Autowired
    private PasswordEncoder passwordEncoder;


    public User saveUser(User user) { 
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("No user found with Id :" + id));
    }

    // Other service methods
}
