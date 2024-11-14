package com.learn.api.user_management_2.user.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.learn.api.user_management_2.user.entities.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Optional<Permission> findByPermission(String permission);

    @Query(value = "SELECT COUNT(*) FROM permissions_roles WHERE permission_id = ?1", nativeQuery = true) // nativeQuery: Attribut utilisé dans l'annotation @query pour indiquer que  la requête est native et est exécutée directement sur la base de données, sans être traduite en JPQL
    Integer countPermissionUsage(Integer permissionId);

    void deleteByPermission(String permission);

}
