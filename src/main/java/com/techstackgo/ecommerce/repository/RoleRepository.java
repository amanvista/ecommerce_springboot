package com.techstackgo.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techstackgo.ecommerce.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
