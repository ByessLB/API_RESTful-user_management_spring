package com.learn.api.user_management_2.user.exceptions;

import lombok.Data;

@Data
public class ErrorDetails implements java.io.Serializable {

    private final String message;

    private final long timestamp;

    public ErrorDetails(final String message) {
        this.timestamp = System.currentTimeMillis();
        this.message = message;
    }

}
