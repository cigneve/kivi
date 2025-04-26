package com.traveller.exception;

/**
 * UserNotFoundException is thrown whenever a request tries to operate on an
 * invalid User.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
