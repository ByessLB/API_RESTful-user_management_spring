package com.learn.api.user_management_2.user.dtos;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

/**
 * DTO for the list of users
 */
@Data
public class UserListDTO implements Serializable {

    private ArrayList<UserDTO> userList;

    public UserListDTO() {
        userList = new ArrayList<>();
    }
}
