package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.response.ErrorDto;
import com.nhnacademy.taskapi.exception.AccountNotFoundException;
import com.nhnacademy.taskapi.exception.AccountNotMemberException;
import com.nhnacademy.taskapi.exception.ResourceAlreadyExistException;
import com.nhnacademy.taskapi.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
        @ExceptionHandler(AccountNotFoundException.class)
        public ResponseEntity<ErrorDto> accountNotFoundException(AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorDto(404, e.getMessage())
            );
        }
        @ExceptionHandler(AccountNotMemberException.class)
        public ResponseEntity<ErrorDto> accountMemberException(AccountNotMemberException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    new ErrorDto(403, e.getMessage())
            );
        }
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorDto> resourceNotFoundException(ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorDto(404, e.getMessage())
            );
        }
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorDto> illegalArgumentException(IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorDto(400, e.getMessage())
            );
        }
        @ExceptionHandler(ResourceAlreadyExistException.class)
        public ResponseEntity<ErrorDto> resourceAlreadyExistException(ResourceAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorDto(409, e.getMessage())
            );
        }

//
//        @ExceptionHandler(AccountNotFoundException.class)
//        public ResponseEntity<ErrorDto> accountNotFoundException(AccountNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    new ErrorDto(404, e.getMessage())
//            );
//        }
//    }
}
