package com.learn.api.user_management_2.user.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.api.user_management_2.user.dtos.PermissionDTO;
import com.learn.api.user_management_2.user.entities.Permission;
import com.learn.api.user_management_2.user.exceptions.InvalidPermissionDataException;
import com.learn.api.user_management_2.user.exceptions.PermissionInUseException;
import com.learn.api.user_management_2.user.exceptions.PermissionNotFoundException;
import com.learn.api.user_management_2.user.repositories.PermissionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PermissionService {

    private static final Logger log = LoggerFactory.getLogger(PermissionService.class);

    @Autowired
    private PermissionRepository permissionRepository;

    public Iterable<Permission> getPermissionList() {
        return permissionRepository.findAll();
    }

    /**
     * Get permission by key
     *
     * @param key
     * @return
     */
    public Permission getPermissionByKey(String key) {
        if (key == null || key.isEmpty()) {
            throw new InvalidPermissionDataException("Permission key cannot be null or empty");
        }
        Optional<Permission> userOpt = permissionRepository.findByPermission(key);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }
        throw new PermissionNotFoundException(String.format("Permission not found for permission key = %s", key));
    }

    /**
     * Create permission
     *
     * @param permisionDTO
     * @return
     */
    @Transactional
    public Permission createPermission(PermissionDTO permissionDTO) {
        if (permissionDTO == null) {
            throw new InvalidPermissionDataException("Permission data cannot be null or empty");
        }

        String permissionKey = permissionDTO.getPermission();

        if (permissionKey == null || permissionKey.isEmpty()) {
            throw new InvalidPermissionDataException("Permission key cannot be null or empty");
        }

        // Check permission
        Optional<Permission> permissionOpt = permissionRepository.findByPermission(permissionKey);
        if (permissionOpt.isPresent()) {
            throw new PermissionNotFoundException((String.format("Permission %s already existing with the same key with Id = %s", permissionKey, permissionOpt.get().getId())));
        }

        Permission permission = new Permission();
        permission.setPermission(permissionKey);

        permission.setNote(permissionDTO.getNote());
        permission.setEnabled(permissionDTO.isEnabled());

        Permission createdPermission = permissionRepository.save(permission);

        log.info(String.format("Created permission %s with Id = %s", permissionKey, createdPermission.getId()));
        return createdPermission;
    }

    /**
     * Update permission
     *
     * @param permissionDTO
     * @return
     */

    @Transactional
    public Permission updatePermission(PermissionDTO permissionDTO) {
        if (permissionDTO == null) {
            throw new InvalidPermissionDataException("Permission data cannot be null");
        }

        Integer permissionId = permissionDTO.getId();
        Optional<Permission> permissionOpt = permissionRepository.findById(permissionId);
        if (!permissionOpt.isPresent()) {
            throw new PermissionNotFoundException(String.format("The permission with the id = %s has not been found", permissionId));
        }

        Permission permission = permissionOpt.get();
        String permissionKey = permissionDTO.getPermission();

        // Check if exists a different configuration with the same permissionKey
        Optional<Permission> permissionByKeyOpt = permissionRepository.findByPermission(permissionKey);
        if (permissionByKeyOpt.isPresent()) {
            Permission permission1 = permissionByKeyOpt.get();
            if (!permission1.getId().equals(permission.getId())) {
                throw new InvalidPermissionDataException(String.format("Exists already a permission with the key %s. Use another key", permissionKey));
            }
        }

        // Update permission
        permission.setPermission(permissionDTO.getPermission());
        permission.setEnabled(permissionDTO.isEnabled());
        permission.setNote(permissionDTO.getNote());

        Permission updatePermission = permissionRepository.save(permission);
        log.info(String.format("Permission with id = %s has been updated", permission.getId()));

        return updatePermission;
    }

    /**
     * Delete permission by key
     *
     * @param permissionKey
     */
    @Transactional
    public void deletePermissionByKey(String permissionKey) {
        if (permissionKey == null || permissionKey.isEmpty()) {
            throw new InvalidPermissionDataException("Permission key cannot be null or empty");
        }

        // Check permission
        Optional<Permission> permissionOpt = permissionRepository.findByPermission(permissionKey);
        if (!permissionOpt.isPresent()) {
            throw new PermissionNotFoundException(String.format("Permission %s not found", permissionKey));
        }

        Permission permission = permissionOpt.get();

        // check usage
        Integer rowsFound = permissionRepository.countPermissionUsage(permission.getId());
        if (rowsFound > 0) {
            // Permission cannot be deleted
            throw new PermissionInUseException(String.format("The permission with key %s is in used (%s configuration rows)", permissionKey, rowsFound));
        }

        permissionRepository.delete(permission);

        log.info(String.format("Deleted permission with key %s", permission.getPermission()));
    }
}
