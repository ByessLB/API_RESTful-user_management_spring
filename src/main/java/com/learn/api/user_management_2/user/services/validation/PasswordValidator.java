package com.learn.api.user_management_2.user.services.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.learn.api.user_management_2.user.exceptions.InvalidUserDataException;

public class PasswordValidator {

    private static final int  MAX_PASSWORD_LENGTH = 60;

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,}$";

    private Pattern pattern;

    public PasswordValidator() {
        pattern = Pattern.compile(PASSWORD_REGEX);
    }

    public void checkPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new InvalidUserDataException("Password cannot be null or empty");
        }

        // Check max length
        if (password.length() > MAX_PASSWORD_LENGTH) {
            throw new InvalidUserDataException(String.format("Password is too long: max number of chars is %s",  MAX_PASSWORD_LENGTH));
        }

        // Password msut to be at least 8 chars, 1 number, 1 upper case, 1 lower case letter, 1 special char, no spaces
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new InvalidUserDataException("Password must to be at least 8 chars, 1 number, 1 upper case, 1 loxer case letter, 1 special char, no spaces. ");
        }
    }
}
