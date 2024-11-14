package com.learn.api.user_management_2.user.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.api.user_management_2.user.dtos.PermissionDTO;
import com.learn.api.user_management_2.user.dtos.RoleDTO;
import com.learn.api.user_management_2.user.entities.Permission;
import com.learn.api.user_management_2.user.entities.Role;
import com.learn.api.user_management_2.user.services.PermissionService;
import com.learn.api.user_management_2.user.services.RoleService;

@RestController
@RequestMapping(value = "/users/rbac")
public class RBACRestController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    // Roles
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getRolePresentationList() {
        Iterable<Role> roleList = roleService.getRoleList();
        ArrayList<RoleDTO> list = new ArrayList<>();
        roleList.forEach(e -> list.add(new RoleDTO(e)));
        return ResponseEntity.ok(list);
    }

    @PostMapping("/roles")
    public ResponseEntity<RoleDTO> createRole(@RequestBody String role) {
        return new ResponseEntity<>(new RoleDTO(roleService.createRole(role)), null, HttpStatus.CREATED);
    }

    @GetMapping("/roles/{roleId}")
    public RoleDTO getRoleById(@PathVariable("roleId") Integer roleId) {
        return new RoleDTO(roleService.getRoleById(roleId));
    }

    @DeleteMapping("/roles/{roleId}")
    public ResponseEntity<?> deleteRoleById(@PathVariable("roleId") Integer roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.noContent().build();
    }

    // retreive the permission's list
    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionDTO>> getPermissionPresentationList() {
        Iterable<Permission> permissionList = permissionService.getPermissionList();
        ArrayList<PermissionDTO> list = new ArrayList<>();
        permissionList.forEach(e -> list.add(new PermissionDTO(e)));
        return ResponseEntity.ok(list);
    }

    // Permissions
    @GetMapping("/permissions/{permissionKey}")
    public ResponseEntity<PermissionDTO> getPermissionByKey(@PathVariable("permissionKey") String permissionKey) {
        PermissionDTO permissionDTO = new PermissionDTO(permissionService.getPermissionByKey(permissionKey));
        return ResponseEntity.ok(permissionDTO);
    }

    @PostMapping("/permissions")
    public ResponseEntity<PermissionDTO> createPermission(@RequestBody PermissionDTO permissionDTO) {
        return new ResponseEntity<>(new PermissionDTO(permissionService.createPermission(permissionDTO)), null, HttpStatus.CREATED);
    }

    @PutMapping("/permissions")
    public ResponseEntity<PermissionDTO> updatePermission(@RequestBody PermissionDTO permissionDTO) {
        return new ResponseEntity<>(new PermissionDTO(permissionService.updatePermission(permissionDTO)), null, HttpStatus.CREATED);
    }

    @DeleteMapping("/permissions/{permissionKey}")
    public ResponseEntity<?> deletePermissionByKey(@PathVariable("permissionKey") String permissionKey) {
        permissionService.deletePermissionByKey(permissionKey);
        return ResponseEntity.noContent().build();
    }

    // Add or remove a permission on a role
    @PostMapping("/roles/{roleId}/permissions/{permissionKey}")
    public ResponseEntity<RoleDTO> addPermissionOnRole(@PathVariable("roleId") Integer roleId, @PathVariable("permissionKey") String permissionKey) {
        return new ResponseEntity<>(new RoleDTO(roleService.addPermissionOnRole(roleId, permissionKey)), null, HttpStatus.CREATED);
    }

    @DeleteMapping("/roles/{roleId}/permissions/{permissionKey}")
    public ResponseEntity<RoleDTO> removePermissionOnRole(@PathVariable("roleId") Integer roleId, @PathVariable("permissionKey") String permissionKey) {
        return new ResponseEntity<>(new RoleDTO(roleService.removePermissionOnRole(roleId, permissionKey)), null, HttpStatus.OK);
    }
}
