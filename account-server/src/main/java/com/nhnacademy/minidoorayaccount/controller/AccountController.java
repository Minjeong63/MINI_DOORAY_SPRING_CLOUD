package com.nhnacademy.minidoorayaccount.controller;

import com.nhnacademy.minidoorayaccount.dto.request.AccountDto;
import com.nhnacademy.minidoorayaccount.dto.request.AccountStatusDto;
import com.nhnacademy.minidoorayaccount.dto.response.DefaultDto;
import com.nhnacademy.minidoorayaccount.entity.Account;
import com.nhnacademy.minidoorayaccount.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/{userId}")
    public ResponseEntity<DefaultDto<Account>> getAccountByUserId(@PathVariable String userId) {
        Account account = accountService.getAccount(userId);
        return ResponseEntity.ok().body(new DefaultDto<>(200, account));
    }

    @GetMapping
    public ResponseEntity<DefaultDto<Account>> getAccount(@RequestHeader("accountId") String accountId) {
        Account account = accountService.getAccount(Long.parseLong(accountId));
        DefaultDto<Account> dto = new DefaultDto<>(200, account);
        return ResponseEntity.ok().body(dto);
    }

    // 사용자의 최근 상태 업데이트
    @PatchMapping
    public ResponseEntity<DefaultDto<Account>> updateAccountStatus(@RequestHeader("accountId") String accountId,
                                                       @RequestBody AccountStatusDto statusDto) {
        Account account = accountService.updateStatus(Long.parseLong(accountId), statusDto);
        DefaultDto<Account> dto = new DefaultDto<>(200, account);
        return ResponseEntity.ok().body(dto);
    }
}
