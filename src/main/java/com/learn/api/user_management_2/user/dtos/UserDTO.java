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

@Data
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
}
