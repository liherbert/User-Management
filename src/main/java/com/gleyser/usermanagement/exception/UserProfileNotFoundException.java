package com.gleyser.usermanagement.exception;

public class UserProfileNotFoundException extends Exception{

    public UserProfileNotFoundException(Long id) {
        super("User Profile not found with id: " + id);
    }
}
