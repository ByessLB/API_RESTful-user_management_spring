package com.learn.api.user_management_2.user.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.learn.api.user_management_2.user.entities.Role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode
public class RoleDTO implements Serializable {

    private Integer id;
    private String role;

    private List<PermissionDTO> permissions = new ArrayList<>();

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.role = role.getRole();

        // Permissions
        role.getPermissions().stream().forEach(e -> permissions.add(new PermissionDTO(e)));
    }

    public RoleDTO(Integer id, String role) {
        this.id = id;
        this.role = role;
    }
}
