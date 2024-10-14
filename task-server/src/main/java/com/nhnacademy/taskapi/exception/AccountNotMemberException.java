package com.nhnacademy.taskapi.exception;

public class AccountNotMemberException extends RuntimeException {
    public AccountNotMemberException(Long accountId) {
        super("Account with" + accountId + "is not project member");
    }
}
