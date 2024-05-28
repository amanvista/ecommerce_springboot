package com.techstackgo.ecommerce.repository;

import com.techstackgo.ecommerce.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByEmail(String email);

    Optional<User> findByUsernameAndIsEnabledTrue(String username);
}
