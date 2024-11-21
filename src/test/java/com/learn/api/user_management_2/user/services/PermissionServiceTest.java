package com.learn.api.user_management_2.user.services;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.learn.api.user_management_2.user.entities.Permission;
import com.learn.api.user_management_2.user.repositories.PermissionRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;


@ExtendWith(MockitoExtension.class)
public class PermissionServiceTest {

    @Mock
    private PermissionRepository permissionRepository;

    @Autowired
    @InjectMocks
    private PermissionService permissionService;

    @Test
    public void calling_getPermissionList_then_return_list_of_permission() {
        ArrayList<Permission> permissionArrayList = new ArrayList<>();

        Permission permission1 = new Permission(2, "LOGIN");
        Permission permission2 = new Permission(3, "VIEW_PROFILE");

        permissionArrayList.add(permission1);
        permissionArrayList.add(permission2);

        when(permissionRepository.findAll()).thenReturn(permissionArrayList);

        List<Permission> permissionList = (List<Permission>) permissionService.getPermissionList();

        assertNotNull(permissionList);

        assertEquals(2, permissionList.size());
        assertTrue(permissionList.contains(new Permission(2, "LOGIN")));
        assertTrue(permissionList.contains(new Permission(2, "VIEW_PROFILE")));
    }

    @Test
    public void calling_getPermissionByKey_then_return_permission() {
        ArrayList<Permission> permissionArrayList = new ArrayList<>();

        Permission permission1 = new Permission(2, "LOGIN");
        Permission permission2 = new Permission(3, "VIEW_PROFILE");

        permissionArrayList.add(permission1);
        permissionArrayList.add(permission2);

        when(permissionRepository.findByPermission("LOGIN")).thenReturn(Optional.of(permission1));

        Permission permission = permissionService.getPermissionByKey("LOGIN");

        assertNotNull(permission);

        verify(permissionRepository.findByPermission("LOGIN"));
    }
}
