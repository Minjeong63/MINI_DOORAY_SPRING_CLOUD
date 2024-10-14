package com.nhnacademy.minidoorayaccount.service;

import com.nhnacademy.minidoorayaccount.dto.request.AccountDto;
import com.nhnacademy.minidoorayaccount.dto.request.AccountStatusDto;
import com.nhnacademy.minidoorayaccount.entity.Account;
import com.nhnacademy.minidoorayaccount.entity.enums.UserStatus;
import com.nhnacademy.minidoorayaccount.exception.AccountAlreadyExistException;
import com.nhnacademy.minidoorayaccount.exception.AccountNotFoundException;
import com.nhnacademy.minidoorayaccount.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Account saveAccount(AccountDto accountDto) {
        // id의 가입 정보가 이미 존재하면 예외 처리
        Account account = accountRepository.findByUserId(accountDto.userId()).orElse(null);

        if (account != null) {
            // 찾은 계정이 탈퇴 상태가 아니면 예외
            if (!account.getStatus().equals(UserStatus.WITHDRAWN)) {
                throw new AccountAlreadyExistException(accountDto.userId());
            }

            // 탈퇴 상태면 정보 업데이트 처리
            account.setPassword(accountDto.password());
            account.setEmail(accountDto.email());
            account.setStatus(UserStatus.REGISTERED);
        } else {
            // 계정 존재하지 않으면 새 계정 생성
            // 비밀번호는 암호화 후 등록됨
            account = new Account(
                    accountDto.userId(),
                    accountDto.password(),
                    accountDto.email(),
                    UserStatus.REGISTERED
            );
        }
        return accountRepository.save(account);
    }

    public Account getAccount(String userId) {
        Account account =
                accountRepository.findByUserId(userId).orElseThrow(() -> new AccountNotFoundException(userId));
        if (account.getStatus().equals(UserStatus.WITHDRAWN)) {
            throw new AccountNotFoundException(userId);
        }

        return account;
    }

    public Account getAccount(Long accountId) {
        Account account =
                accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
        if (account.getStatus() == UserStatus.WITHDRAWN) {
            throw new AccountNotFoundException(accountId);
        }
        return account;
    }

    public Account updateStatus(Long accountId, AccountStatusDto statusDto) {
        Account account =
                accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
        account.setStatus(statusDto.status());
        accountRepository.save(account);

        return account;
    }
}
