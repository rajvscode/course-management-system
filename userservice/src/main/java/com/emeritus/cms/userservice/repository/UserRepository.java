package com.emeritus.cms.userservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emeritus.cms.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  
   Optional<User> findByUsername(String username);

   List<User> findByRolesContaining(String role);
}
