package com.nhnacademy.minidoorayaccount.controller;

import com.nhnacademy.minidoorayaccount.dto.request.AccountDto;
import com.nhnacademy.minidoorayaccount.dto.response.DefaultDto;
import com.nhnacademy.minidoorayaccount.dto.response.RegisterResponseDto;
import com.nhnacademy.minidoorayaccount.entity.Account;
import com.nhnacademy.minidoorayaccount.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<DefaultDto<RegisterResponseDto>> registerAccount(@RequestBody AccountDto accountDto) {
        Account account = accountService.saveAccount(accountDto);
        RegisterResponseDto registerResponseDto = new RegisterResponseDto(account.getAccountId());
        DefaultDto<RegisterResponseDto> dto = new DefaultDto<>(201, registerResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
