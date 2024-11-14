package com.learn.api.user_management_2.user.dtos.requests;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Support the creation of a new user account with a minimum set of required
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserAccountDTO implements Serializable {

    private String username;
    private String password;

    private String lastname;
    private String firstname;
    private String email;
    private String gender;
}
