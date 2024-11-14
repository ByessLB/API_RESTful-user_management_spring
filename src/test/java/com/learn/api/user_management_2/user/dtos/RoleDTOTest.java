package com.learn.api.user_management_2.user.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.learn.api.user_management_2.user.entities.Role;

public class RoleDTOTest {

    @Test
    public void testRoleDTOConstructor1() {
        Role role = new Role(12, "USER");

        RoleDTO roleDTO = new RoleDTO(role);

        assertEquals(role.getId(), roleDTO.getId());
        assertEquals(role.getRole(), roleDTO.getRole());
    }

    @Test
    public void testRoleDTOConstructor2() {
        RoleDTO roleDTO = new RoleDTO(22, "USER");

        assertEquals(Integer.valueOf(22), roleDTO.getId());
        assertEquals("USER", roleDTO.getRole());
    }

    @Test
    public void testEquals() {
        RoleDTO roleDTO1 = new RoleDTO(12, "USER");
        RoleDTO roleDTO2 = new RoleDTO(12, "USER");

        assertTrue(roleDTO1.equals(roleDTO1));
        assertFalse(roleDTO1.equals("WRONG"));
        assertTrue(roleDTO1.equals(roleDTO2));
    }
}
