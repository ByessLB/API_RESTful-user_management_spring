package com.learn.api.user_management_2.user.services.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.learn.api.user_management_2.user.exceptions.InvalidUserDataException;

public class PasswordValidatorTest {

    private PasswordValidator passwordValidator;

    @BeforeEach
    public void initTest() {
        passwordValidator = new PasswordValidator();
    }

    @Test
    public void given_null_password_when_checkPassword_throw_InvalidUserDataException() {
        String password = null;
        assertThrows(InvalidUserDataException.class, () -> passwordValidator.checkPassword(password));
    }

    @Test
    public void given_empty_password_when_checkPassword_throw_InvalidUserDataException() {
        String password = "";
        assertThrows(InvalidUserDataException.class, () -> passwordValidator.checkPassword(password));
    }

    @Test
    public void given_too_long_password_when_checkPassword_throw_InvalidUserDataException() {
        String password = "012345678901234567890123456789012345678901234567890123456789012645998702186490465496468409651649813051984";
        assertThrows(InvalidUserDataException.class, () -> passwordValidator.checkPassword(password));
    }

    @Test
    public void given_not_valid_password_when_checkPassword_throw_InvalidUserDataException() {
        String password = "aaaa asedfbvkpnerbp 44";
        assertThrows(InvalidUserDataException.class, () -> passwordValidator.checkPassword(password));
    }

    @Test
    public void given_invalid_password_less_than_8_chars_when_checkPassword_then_OK() {
        String password = "Sanchez";
        assertThrows(InvalidUserDataException.class, () -> passwordValidator.checkPassword(password));
    }

    @Test
    public void given_invalid_password_with_spaces_when_checkPassword_then_Ok() {
        String password = "Sanchez 123";
        assertThrows(InvalidUserDataException.class, () -> passwordValidator.checkPassword(password));
    }

    @Test
    public void given_invalid_password_no_lowercase_letter_when_checkPassword_then_OK() {
        String password = "SANCHEZ!123";
        assertThrows(InvalidUserDataException.class, () -> passwordValidator.checkPassword(password));
    }

    @Test
    public void given_invalid_password_no_special_char_when_checkPassword_then_OK() {
        String password = "SANCHEZ123";
        assertThrows(InvalidUserDataException.class, () -> passwordValidator.checkPassword(password));
    }

    @Test
    public void given_valid_password_when_checkPassword_then_OK() {
        String password = "Sanchez!123";
        passwordValidator.checkPassword(password);
    }
}
