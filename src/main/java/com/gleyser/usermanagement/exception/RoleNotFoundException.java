package com.gleyser.usermanagement.exception;

public class RoleNotFoundException extends Exception{

    public RoleNotFoundException(Long id) {
        super("Role not found with id: " + id);
    }
}
