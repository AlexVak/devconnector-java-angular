package com.alexvak.devconnectorrest.exception;


import com.alexvak.devconnectorrest.payload.RegisterRequest;

public class DuplicateUserFoundException extends RuntimeException {

    private static final String DUPLICATE_FOND = "Unable to create new user. A User with email %s already exist.";

    public DuplicateUserFoundException(RegisterRequest registerRequest) {
        super(String.format(DUPLICATE_FOND, registerRequest.getEmail()));
    }
}
