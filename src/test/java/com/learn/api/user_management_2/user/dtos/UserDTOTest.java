package com.learn.api.user_management_2.user.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.learn.api.user_management_2.user.entities.Contact;
import com.learn.api.user_management_2.user.entities.Gender;
import com.learn.api.user_management_2.user.entities.Permission;
import com.learn.api.user_management_2.user.entities.Role;
import com.learn.api.user_management_2.user.entities.User;

public class UserDTOTest {

    @Test
    public void testUserDTOConstructor1() {
        User user = new User();
        user.setId(22);
        user.setLastname("Sanchez");
        user.setFirstname("Pedro");
        user.setUsername("PeSanZ");
        user.setEnabled(true);
        user.setGender(Gender.MALE);
        user.setEnabled(true);
        user.setSecured(false);

        Contact contact = new Contact();
        contact.setEmail("email@email.com");
        contact.setPhone("+33606060606");
        contact.setNote("Test note");

        user.setContact(contact);

        LocalDateTime creationDate = LocalDateTime.of(2023, 5, 9, 12, 30);
        user.setCreationDate(creationDate);

        LocalDateTime updatedDate = LocalDateTime.of(2022, 5, 10, 8, 45);
        user.setUpdatedDate(updatedDate);

        Role roleUser = new Role(Role.USER, "USER");
        Role roleAdmin = new Role(Role.ADMIN, "ADMIN");

        user.getRoles().add(roleAdmin);
        user.getRoles().add(roleUser);

        UserDTO userDTO = new UserDTO(user);

        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getLastname(), user.getLastname());
        assertEquals(userDTO.getFirstname(), user.getFirstname());

        assertTrue(userDTO.isEnabled());
        assertTrue(!userDTO.isSecured());

        // Contact
        ContactDTO contactDTO = userDTO.getContactDTO();
        assertNotNull(contactDTO);

        assertEquals(userDTO.getContactDTO().getEmail(), user.getContact().getEmail());
        assertEquals(userDTO.getContactDTO().getPhone(), user.getContact().getPhone());
        assertEquals(userDTO.getContactDTO().getNote(), user.getContact().getNote());

        assertEquals(userDTO.isEnabled(), user.isEnabled());

        assertEquals(creationDate, userDTO.getCreationDate());
        assertEquals(updatedDate, userDTO.getUpdatedDate());
        assertEquals(null, userDTO.getLoginDate());

        assertNotNull(user.getRoles());

        Set<Role> rolesTest = user.getRoles();

        assertTrue(rolesTest.contains(roleUser));
        assertTrue(rolesTest.contains(roleAdmin));
    }

    @Test
    public void testUserDTOConstructor2() {
        // Test enablesd and disables permissions
        User user = new User();
        user.setId(22);
        user.setUsername("username");
        user.setLastname("lastname");
        user.setFirstname("firstname");
        user.setEnabled(true);
        user.setGender(Gender.MALE);
        user.setEnabled(true);
        user.setSecured(false);

        Contact contact = new Contact();
        contact.setEmail("email");
        contact.setPhone("phone");
        contact.setNote("note");

        user.setContact(contact);

        LocalDateTime creationDate = LocalDateTime.of(2024, 2, 21, 12, 30);
        user.setCreationDate(creationDate);

        LocalDateTime updatedDate = LocalDateTime.of(2024, 3, 12, 4, 37);
        user.setUpdatedDate(updatedDate);

        // Create a set of roles and permissions
        Role roleUser = new Role(Role.USER, "USER");
        Role roleAdmin = new Role(Role.ADMIN, "ADMIN");

        Permission p1 = new Permission(22, "LOGIN", true, "Login");
        Permission p2 = new Permission(23, "VIEW_PROFILE", true, "View Profile");
        Permission p3 = new Permission(24, "EDIT_PROFILE", false, "Edit Profile");
        Permission p4 = new Permission(25, "ADMIN_PROFILES", true, "Manage users");

        roleUser.getPermissions().add(p1);
        roleUser.getPermissions().add(p2);

        roleAdmin.getPermissions().add(p3);
        roleAdmin.getPermissions().add(p4);

        user.getRoles().add(roleAdmin);
        user.getRoles().add(roleUser);

        UserDTO userDTO = new UserDTO(user);

        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(userDTO.getLastname(), user.getLastname());
        assertEquals(userDTO.getFirstname(), user.getFirstname());

        assertTrue(userDTO.isEnabled());
        assertTrue(!userDTO.isSecured());

        // Contact
        ContactDTO contactDTO = userDTO.getContactDTO();
        assertNotNull(contactDTO);

        assertEquals(userDTO.getContactDTO().getEmail(), user.getContact().getEmail());
        assertEquals(userDTO.getContactDTO().getPhone(), user.getContact().getPhone());
        assertEquals(userDTO.getContactDTO().getNote(), user.getContact().getNote());

        assertEquals(userDTO.isEnabled(), user.isEnabled());

        assertEquals(creationDate, userDTO.getCreationDate());
        assertEquals(updatedDate, userDTO.getUpdatedDate());
        assertEquals(null, userDTO.getLoginDate());

        assertNotNull(user.getRoles());

        Set<Role> rolesTest = user.getRoles();

        assertTrue(rolesTest.contains(roleUser));
        assertTrue(rolesTest.contains(roleAdmin));

        assertEquals(2, userDTO.getRoles().size());
        assertTrue(userDTO.getRoles().contains("USER"));
        assertTrue(userDTO.getRoles().contains("ADMIN"));

        assertEquals(2, userDTO.getRoles().size());
        assertEquals(3, userDTO.getPermissions().size());

        assertEquals(3, userDTO.getPermissions().size());
        assertTrue(userDTO.getPermissions().contains("LOGIN"));
        assertTrue(userDTO.getPermissions().contains("VIEW_PROFILE"));

        assertFalse(userDTO.getPermissions().contains("EDIT_PROFILE"));
        assertTrue(userDTO.getPermissions().contains("ADMIN_PROFILES"));
    }
}
