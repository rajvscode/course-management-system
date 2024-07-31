package com.emeritus.cms.userservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emeritus.cms.userservice.model.User;
import com.emeritus.cms.userservice.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public String getUserRoles(String username) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        byUsername.orElse(null);
        User user = byUsername.get();
        return user.getRoles();
    }

    public List<User> findAllStudents() {
        return userRepository.findByRolesContaining("STUDENT");
    }

    public List<User> findAllInstructors() {
        return userRepository.findByRolesContaining("INSTRUCTOR");
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public String encode(String pass) {
        return passwordEncoder.encode(pass);
    }
}
