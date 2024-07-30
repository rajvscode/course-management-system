package com.emeritus.cms.userservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String getUserRoles(String username) {
        Optional<User> byUsername = userRepository.findByUsername(username);
        byUsername.orElse(null);
        User user = byUsername.get();
        return user.getRoles();
    }

    public List<User> getAllStudents() {
        return userRepository.findByRolesContaining("STUDENT");
    }

    public List<User> getAllInstructors() {
        return userRepository.findByRolesContaining("INSTRUCTOR");
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
