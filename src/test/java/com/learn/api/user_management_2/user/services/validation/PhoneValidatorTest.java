package com.learn.api.user_management_2.user.services.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.learn.api.user_management_2.user.exceptions.InvalidUserDataException;

public class PhoneValidatorTest {

    private PhoneValidator phoneValidator = new PhoneValidator();

    @Test
    public void given_nullPhone_xhen_checkPhone_then_InvalidUserDataException() {
        Throwable exception = assertThrows(InvalidUserDataException.class, () -> {
            phoneValidator.checkPhone(null);
        });
        assertEquals("The phone cannot be null or empty", exception.getMessage());
    }

    @Test
    public void given_wrong_phone_when_checkPhone_then_InvalidUserDataException() {
        String phone = "ab1234";
        Throwable exception = assertThrows(InvalidUserDataException.class, () -> {
            phoneValidator.checkPhone(phone);
        });
        assertEquals(exception.getMessage(), "The phone provided ab1234 is not formal valid");
    }

    @Test
    public void given_long_phone_when_checkPhone_then_InvalidUserDataException() {
        String phone = "+33 12315664687496513216846516516874985463519459846";
        Throwable exception = assertThrows(InvalidUserDataException.class, () -> {
            phoneValidator.checkPhone(phone);
        });
        assertEquals(exception.getMessage(), "The phone is too long: max number of chars is 20");
    }

    @Test
    public void given_validPhone1_when_checkPhone_OK() {
        String phone = "+33 1234567890";
        phoneValidator.checkPhone(phone);
    }

    @Test
    public void given_validPhone2_with_noSpaces_when_checkPhone_thon_OK() {
        String phone = "+33606060606";
        phoneValidator.checkPhone(phone);
    }
}
