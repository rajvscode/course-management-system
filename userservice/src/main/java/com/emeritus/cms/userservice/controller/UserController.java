package com.emeritus.cms.userservice.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emeritus.cms.userservice.model.AuthRequest;
import com.emeritus.cms.userservice.model.User;
import com.emeritus.cms.userservice.service.JwtService;
import com.emeritus.cms.userservice.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/users")
@EnableMethodSecurity
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.findUserByUsername(user.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        User createdUser = userService.registerUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Update user details
    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@RequestBody User userDetails) {
        Optional<User> userOpt = userService.findUserById(userDetails.getId());
        if (!userOpt.isPresent()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = userOpt.get();
        user.setUsername(userDetails.getUsername());
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(userService.encode(userDetails.getPassword())); // Update password if
                                                                                               // provided
        }
        user.setRoles(userDetails.getRoles());
        User updatedUser = userService.updateUser(user);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Get all instructors
    @GetMapping("/instructors")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllInstructors() {
        List<User> instructors = userService.findAllInstructors();
        return new ResponseEntity<>(instructors, HttpStatus.OK);
    }

    // Get all students
    @GetMapping("/students")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllStudents() {
        List<User> students = userService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // Get all users
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_INSTRUCTOR') or hasAuthority('ROLE_STUDENT')")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        Optional<User> userOpt = userService.findUserById(id);
        if (!userOpt.isPresent()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/token")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            Optional<User> userOpt = userService.findUserByUsername(authRequest.getUsername());
            User user = userOpt.get();
            // System.out.println(Long.toString(user.getId()));

            String token = jwtService.generateToken(authRequest.getUsername(),
                    userService.getUserRoles(authRequest.getUsername()), Long.toString(user.getId()));
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Could not create Token", HttpStatus.BAD_REQUEST);
        }
    }

}
