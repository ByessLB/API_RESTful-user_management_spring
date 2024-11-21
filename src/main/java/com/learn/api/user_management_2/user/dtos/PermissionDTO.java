package com.learn.api.user_management_2.user.dtos;

import java.io.Serializable;

import com.learn.api.user_management_2.user.entities.Permission;


public class PermissionDTO implements Serializable {

    private Integer id;
    private String permission;
    private boolean enabled;
    private String note;

    // Constructor default
    public PermissionDTO() {}

    public PermissionDTO(Permission permission) {
        this.id = permission.getId();
        this.permission = permission.getPermission();
        this.enabled = permission.isEnabled();
        this.note = permission.getNote();
    }

    // Getter & Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
