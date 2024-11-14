package com.learn.api.user_management_2.user.services.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.learn.api.user_management_2.user.exceptions.InvalidUserDataException;

public class PhoneValidator {

    private static int MAX_PHONE_LENGTH = 20;

    private static final String PHONE_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";

    private Pattern pattern;

    public PhoneValidator() {
        pattern = Pattern.compile(PHONE_REGEX);
    }

    public void checkPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new InvalidUserDataException("The phone cannot be null or empty");
        }

        // Check max phone length
        if (phone.length() > MAX_PHONE_LENGTH) {
            throw new InvalidUserDataException(String.format("The phone is too long: max number of chars is %s",  MAX_PHONE_LENGTH));
        }

        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            throw new InvalidUserDataException(String.format("The phone provided %s is not formal valid", phone));

        }
    }
}
