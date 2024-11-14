package com.learn.api.user_management_2.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.learn.api.user_management_2.user.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRole(String role);

    @Query(value = "SELECT COUNT(*) FROM users_roles WHERE role_id = ?1", nativeQuery = true)
    Integer countRoleUsage(Integer roleId);
}
