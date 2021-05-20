package com.gleyser.usermanagement.exception;

public class DuplicateNameException extends Exception {
    public DuplicateNameException() {
        super("The name must be unique");
    }
}



