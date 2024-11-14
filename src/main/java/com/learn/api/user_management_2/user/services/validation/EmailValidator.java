package com.learn.api.user_management_2.user.services.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.learn.api.user_management_2.user.exceptions.InvalidUserDataException;

public class EmailValidator {

    private static final int MAX_EMAIL_LENGTH = 255;

    private static final String EMAIL_REGEX = "^[\\w!#$%&'+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    private Pattern pattern;

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_REGEX);
    }

    public void checkEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new InvalidUserDataException("The Email cannot be null or empty");
        }

        // Check max email length
        if (email.length() > MAX_EMAIL_LENGTH) {
            throw new InvalidUserDataException(String.format("The Email is too long: max number of chars is %s",  MAX_EMAIL_LENGTH));
        }

        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidUserDataException(String.format("The Email provided %s is not formal valid", email));
        }
    }
}
