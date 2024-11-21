package com.learn.api.user_management_2.user.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.learn.api.user_management_2.user.entities.Permission;
import com.learn.api.user_management_2.user.entities.Role;
import com.learn.api.user_management_2.user.entities.User;

import lombok.Data;

public class UserDTO implements Serializable {

    private Integer id;
    private String lastname;
    private String firstname;
    private String username;
    private String gender;
    private LocalDate birthDate;

    private boolean enabled;

    private String note;

    private LocalDateTime creationDate;
    private LocalDateTime updatedDate;
    private LocalDateTime loginDate;

    private boolean secured;

    private ContactDTO contactDTO;
    private AddressDTO addressDTO;

    // Permissions and roles list
    private List<String> roles;
    private List<String> permissions;

    public UserDTO() {
        // Empty constructor
        roles = new ArrayList<>();
        permissions = new ArrayList<>();
    }

    public UserDTO(User user) {
        if (user != null) {
            this.id = user.getId();
            this.lastname = user.getLastname();
            this.firstname = user.getFirstname();
            this.username = user.getUsername();
            this.gender = user.getGender().name();

            this.birthDate = user.getBirthDate();

            this.enabled = user.isEnabled();

            this.note = user.getNote();

            this.creationDate = user.getCreationDate();
            this.updatedDate = user.getUpdatedDate();
            this.loginDate = user.getLoginDate();

            this.secured = user.isSecured();

            // Contact, if set
            if (user.getContact() != null) {
                this.contactDTO = new ContactDTO(user.getContact());
            }

            // Address, if set
            if (user.getAddress() != null) {
                this.addressDTO = new AddressDTO(user.getAddress());
            }

            /*
             * Because the permissions can be associated to more than one roles
             * I'm creating two String arrays with the distinct keys of roles
             * and permissions
             */
            roles = new ArrayList<>();
            permissions = new ArrayList<>();

            for (Role role : user.getRoles()) {
                roles.add(role.getRole());
                for (Permission p : role.getPermissions()) {
                    String key = p.getPermission();
                    if ((!permissions.contains(key)) && (p.isEnabled())) {
                        // Add the permission only if enabled
                        permissions.add(key);
                    }
                }
            }
        }
    }

    // Getter & Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalDateTime getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(LocalDateTime loginDate) {
        this.loginDate = loginDate;
    }

    public boolean isSecured() {
        return secured;
    }

    public void setSecured(boolean secured) {
        this.secured = secured;
    }

    public ContactDTO getContactDTO() {
        return contactDTO;
    }

    public void setContactDTO(ContactDTO contactDTO) {
        this.contactDTO = contactDTO;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
