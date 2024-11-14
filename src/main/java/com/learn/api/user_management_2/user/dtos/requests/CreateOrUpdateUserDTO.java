package com.learn.api.user_management_2.user.dtos.requests;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create or modify user data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrUpdateUserDTO implements Serializable {

    private String username;
    private String password;

    private String lastname;
    private String firstname;
    private String gender;
    private LocalDate birthDate;

    private boolean enabled;
    private boolean secured;

    private String note;

    // Contact information
    private String email;
    private String phone;
    private String contactNote;

    // Address information
    private String address;
    private String address2;
    private String city;
    private String country;
    private String zipCode;
}
