package com.nhnacademy.taskapi.controller;

import com.nhnacademy.taskapi.dto.response.DefaultDto;
import com.nhnacademy.taskapi.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/{accountId}")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<DefaultDto<Object>> addAccount(@PathVariable Long accountId){
        accountService.addAccount(accountId);

        DefaultDto<Object> dto = new DefaultDto<>(201, null);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
