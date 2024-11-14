package com.learn.api.user_management_2.user.services.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.learn.api.user_management_2.user.exceptions.InvalidUserDataException;

public class EmailValidatorTest {

    private EmailValidator emailValidator;

    @BeforeEach
    public void initTest() {
        emailValidator = new EmailValidator();
    }

    @Test
    public void given_null_email_checkEmail_throw_InvalidUserDataException(){
        String email = null;
        assertThrows(InvalidUserDataException.class, () -> emailValidator.checkEmail(email));
    }

    @Test
    public void given_empty_email_checkEmail_throw_InvalidUserDataException(){
        String email = "";
        assertThrows(InvalidUserDataException.class, () -> emailValidator.checkEmail(email));
    }

    @Test
    public void given_invalid_email_checkEmail_throw_InvalidUserDataException(){
        String email = "@mail.com";
        assertThrows(InvalidUserDataException.class, () -> emailValidator.checkEmail(email));
    }

    @Test
    public void given_valid_email_when_checkEmail_OK(){
        String email = "testmail@gmail.com";
        emailValidator.checkEmail(email);
    }
}
