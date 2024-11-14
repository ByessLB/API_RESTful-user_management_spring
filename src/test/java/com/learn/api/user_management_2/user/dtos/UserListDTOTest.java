package com.learn.api.user_management_2.user.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class UserListDTOTest {

    @Test
    public void testUserListDTO() {
        UserListDTO userListDTO = new UserListDTO();

        assertNotNull(userListDTO.getUserList().size());
        assertEquals(0, userListDTO.getUserList().size());
    }
}
