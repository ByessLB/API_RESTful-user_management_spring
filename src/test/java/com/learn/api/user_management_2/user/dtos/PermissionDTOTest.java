package com.learn.api.user_management_2.user.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.learn.api.user_management_2.user.entities.Permission;

public class PermissionDTOTest {

    @Test
    public void testPermissionDTOConstructor1() {
        PermissionDTO permissionDTO = new PermissionDTO();
        assertEquals(null, permissionDTO.getId());
        assertEquals(null, permissionDTO.getPermission());
    }

    @Test
    public void testPermissionDTOConstructor2() {
        Permission permission = new Permission(134, "Browse website");

        PermissionDTO permissionDTO = new PermissionDTO(permission);

        assertEquals(permission.getId(), permissionDTO.getId());
        assertEquals(permission.getPermission(), permissionDTO.getPermission());
        assertTrue(permissionDTO.isEnabled());
    }
}
