package com.learn.api.user_management_2.user.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.api.user_management_2.user.entities.Permission;
import com.learn.api.user_management_2.user.entities.Role;
import com.learn.api.user_management_2.user.exceptions.InvalidPermissionDataException;
import com.learn.api.user_management_2.user.exceptions.InvalidRoleDataException;
import com.learn.api.user_management_2.user.exceptions.InvalidRoleIdentifierException;
import com.learn.api.user_management_2.user.exceptions.PermissionNotFoundException;
import com.learn.api.user_management_2.user.exceptions.RoleInUseException;
import com.learn.api.user_management_2.user.exceptions.RoleNotFoundException;
import com.learn.api.user_management_2.user.repositories.PermissionRepository;
import com.learn.api.user_management_2.user.repositories.RoleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public Iterable<Role> getRoleList() {
        return roleRepository.findAll();
    }

    /**
     * Get role by id
     *
     * @param id
     * @return
     */

    public Role getRoleById(Integer id) {
        if (id == null) {
            throw new InvalidRoleIdentifierException("Id role cannot be null");
        }

        Optional<Role> roleOpt = roleRepository.findById(id);
        if (roleOpt.isPresent()) {
            return roleOpt.get();
        }
        throw new RoleNotFoundException(String.format("Role not found for Id = %s", id));
    }

    /**
     * Validate rolename
     *
     * @param roleName
     */

    public static void validateRoleName(String roleName) {
        if (roleName == null || roleName.isEmpty()) {
            throw new InvalidRoleDataException(String.format("Invalid role name: %s", roleName));
        }
    }

    /**
     * Create role
     *
     * @param roleStr
     * @return
     */

    @Transactional
    public Role createRole(String roleStr) {
        validateRoleName(roleStr);

        // Check roleStr not in use
        if (roleRepository.findByRole(roleStr).isPresent()) {
            String errMsg = String.format("The role %s already exists", roleStr);
            log.error(errMsg);
            throw new RoleInUseException(errMsg);
        }

        Role role = new Role();
        role.setRole(roleStr);

        role = roleRepository.save(role);
        log.info(String.format("Role %s %s has been created.", role.getId(), role.getRole()));

        return role;
    }

    /**
     * Delete role
     *
     * @param id
     */

    @Transactional
    public void deleteRole(Integer id) {
        Optional<Role> roleOpt = roleRepository.findById(id);
        if (!roleOpt.isPresent()) {
            String errMsg = String.format("Role not found for Id = %s cannot be deleted", id);
            log.error(errMsg);
            throw new RoleNotFoundException(errMsg);
        }

        Role role = roleOpt.get();

        // Check if the role is in use
        Integer countUsages = roleRepository.countRoleUsage(id);
        if (countUsages > 0) {
            String errMsg = String.format("The role %s %s is in use (%s users_roles configuration rows) and cannot be deleted", role.getId(), role.getRole());
            log.error(errMsg);
            throw new RoleInUseException(errMsg);
        }

        roleRepository.deleteById(id);
        log.info(String.format("Role %s has been deleted.", id));
    }

    /**
     * Add or remove a permission on a role
     *
     * @param permissionKey
     */
    public static void validatePermissionKey(String permissionKey) {
        if (permissionKey == null || permissionKey.isEmpty()) {
            throw new InvalidPermissionDataException("Permission key cannot be null or empty");
        }
    }

    /**
     * Add permission on role
     *
     * @param roleId
     * @param permissionKey
     * @return
     */

    @Transactional
    public Role addPermissionOnRole(Integer roleId, String permissionKey) {
        validatePermissionKey(permissionKey);

        // Check role
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException(String.format("Role not found with Id = %s", roleId));
        }
        Role role = roleOpt.get();

        // Check if exists the permission key
        Permission permission;

        Optional<Permission> permissionOpt = permissionRepository.findByPermission(permissionKey);
        if (permissionOpt.isPresent()) {
            // the permission exists
            permission = permissionOpt.get();
        } else {
            // If the permission doesn't exists: create one
            permission = new Permission();
            permission.setPermission(permissionKey);

            permission = permissionRepository.save(permission);
        }

        // Check if this role contains already the given permission
        if (role.getPermissions().contains(permission)) {
            throw new InvalidPermissionDataException(String.format("The permission %s has been already associated on the role %s", permission.getPermission(), role.getRole()));
        }

        role.getPermissions().add(permission);
        roleRepository.save(role);

        log.info(String.format("Added permission %s on role id = %s", permissionKey, roleId));
        return roleRepository.findById(roleId).get();
    }

    /**
     * Remove permission on role
     *
     * @param roleId
     * @param permissionKey
     * @return
     */

    @Transactional
    public Role removePermissionOnRole(Integer roleId, String permissionKey) {
        validatePermissionKey(permissionKey);

        // Check role
        Optional<Role> roleOpt = roleRepository.findById(roleId);
        if (!roleOpt.isPresent()) {
            throw new RoleNotFoundException(String.format("Role not found with Id = %s", roleId));
        }
        Role role = roleOpt.get();

        // Check permission
        Optional<Permission> permissiontOpt = permissionRepository.findByPermission(permissionKey);
        if (!permissiontOpt.isPresent()) {
            throw new PermissionNotFoundException(String.format("Permission not found with Id = %s on role %s", permissionKey, roleId));
        }

        Permission permission = permissiontOpt.get();

        role.getPermissions().remove(permission);
        roleRepository.save(role);

        log.info(String.format("Remove permission %s from role id = %s", permissionKey, roleId));
        return roleRepository.findById(roleId).get();
    }
}
