package com.nhnacademy.minidoorayaccount.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(long accountId) {
        super("Account with id " + accountId + " not found");
    }

  public AccountNotFoundException(String userId) {
    super("Account with id " + userId + " not found");
  }
}
