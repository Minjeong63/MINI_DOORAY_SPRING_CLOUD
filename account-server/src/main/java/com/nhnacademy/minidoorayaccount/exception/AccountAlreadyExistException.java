package com.nhnacademy.minidoorayaccount.exception;

public class AccountAlreadyExistException extends RuntimeException {
    public AccountAlreadyExistException(String userId) {
        super("Account with id " + userId + " already exists");
    }
}
