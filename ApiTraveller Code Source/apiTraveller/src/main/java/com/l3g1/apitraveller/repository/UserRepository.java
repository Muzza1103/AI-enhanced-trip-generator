package com.l3g1.apitraveller.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.l3g1.apitraveller.model.User;
@Repository
// Interface extending JpaRepository to handle CRUD operations for the User entity
public interface UserRepository extends JpaRepository<User, Long> {
    // Method to find a user by username
    Optional<User> findByUsername(String username);
    // Method to check if a user exists by username
    Boolean existsByUsername(String username);

    // Method to check if a user exists by email
    Boolean existsByEmail(String email);

    // Method to find a user by id
    Optional<User> findById(Long id);
}