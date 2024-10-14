package com.nhnacademy.taskapi.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String string) {
        super(string);
    }
}
