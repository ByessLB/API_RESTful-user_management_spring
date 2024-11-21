package com.learn.api.user_management_2.user.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.learn.api.user_management_2.user.entities.Role;
import com.learn.api.user_management_2.user.exceptions.InvalidRoleDataException;
import com.learn.api.user_management_2.user.exceptions.InvalidRoleIdentifierException;
import com.learn.api.user_management_2.user.exceptions.RoleInUseException;
import com.learn.api.user_management_2.user.exceptions.RoleNotFoundException;
import com.learn.api.user_management_2.user.repositories.PermissionRepository;
import com.learn.api.user_management_2.user.repositories.RoleRepository;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @Autowired
    @InjectMocks
    private RoleService roleService = new RoleService();

    @Test
    public void given_wrong_roleId_when_getRoleById_throw_InvalidRoleIdentifierException() {
        assertThrows(InvalidRoleIdentifierException.class, () -> {
            roleService.getRoleById(null);
        });
    }

    @Test
    public void given_not_existing_roleId_when_getRoleById_throw_RoleNotFoundException() {
        assertThrows(RoleNotFoundException.class, () -> {
            Integer roleId = 3;
            roleService.getRoleById(roleId);
        });
    }

    @Test
    public void given_existing_roleId_xhen_getRoleById_return_Role() {
        Integer roleId = 2;
        Role role = new Role(roleId, "TEST ROLE");

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        Role returnRole = roleService.getRoleById(roleId);

        assertNotNull(returnRole);
        assertEquals(role.getId(), returnRole.getId());
    }

    // validateRoleName

    @Test
    public void given_invalid_role_name_when_validateRoleName_throw_IvalidRoleDataException() {
        assertThrows(InvalidRoleDataException.class, () -> {
            roleService.validateRoleName(null);
        });
    }

    @Test
    public void given_empty_role_name_when_validateRoleName_throw_InvalidRoleDataException() {
        assertThrows(InvalidRoleDataException.class, () -> {
            roleService.validateRoleName("");
        });
    }

    @Test
    public void given_empty_role_name_when_validateRoleName_no_exception_occurs() {
        roleService.validateRoleName("VALID_ROLE_TEST");
    }

    // CreateRole

    @Test
    public void given_invalid_role_name_when_createRole_throw_InvalidRoleDataException() {
        assertThrows(InvalidRoleDataException.class, () -> {
            roleService.createRole(null);
        });
    }

    @Test
    public void given_valid_used_name_when_createRole_throw_RoleInUseException() {
        assertThrows(RoleInUseException.class, () -> {
        Role role = new Role(1, "TEST");
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

            roleService.createRole("TEST");
        });
    }

    @Test
    public void given_vaid_not_used_name_xhen_createRole_returnRole() {
        Integer genId = 12;
        Role roleData = new Role(genId, "TEST");

        when(roleRepository.save(any(Role.class))).thenReturn(new Role(genId, roleData.getRole()));

        Role role = roleService.createRole("TEST");

        System.out.println(role);

        assertNotNull(role);
        assertEquals(genId, role.getId());
        assertEquals("TEST", role.getRole());
    }

    // DeleteRole

    @Test
    public void given_existing_role_when_deleteRole_throw_RoleNotFoundException() {
        assertThrows(RoleNotFoundException.class, () -> {
            roleService.deleteRole(1);
        });
    }

    @Test
    public void given_existing_role_in_use_xhen_deleteRole_throw_RoleInUseException() {
        assertThrows(RoleInUseException.class, () -> {
            when(roleRepository.findById(1)).thenReturn(Optional.of(new Role(1, "TEST")));
            when(roleRepository.countRoleUsage(1)).thenReturn(10);

            roleService.deleteRole(1);
        });
    }

    
}
