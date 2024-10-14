package com.nhnacademy.minidoorayaccount.service;

import com.nhnacademy.minidoorayaccount.dto.request.AccountDto;
import com.nhnacademy.minidoorayaccount.dto.request.AccountStatusDto;
import com.nhnacademy.minidoorayaccount.entity.Account;
import com.nhnacademy.minidoorayaccount.entity.enums.UserStatus;
import com.nhnacademy.minidoorayaccount.exception.AccountAlreadyExistException;
import com.nhnacademy.minidoorayaccount.exception.AccountNotFoundException;
import com.nhnacademy.minidoorayaccount.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;

    private final Account mockAccountActive = new Account("user", "123", "example@naver.com", UserStatus.REGISTERED);
    private final Account mockAccountWithdrawn = new Account("user", "123", "example@naver.com", UserStatus.WITHDRAWN);
    private final AccountDto testAccountDto = new AccountDto("user", "123", "example@naver.com");

    @Test
    void saveAccount() {
        when(accountRepository.findByUserId("user")).thenReturn(Optional.empty());
        when(accountRepository.save(any())).thenReturn(any(Account.class));
        accountService.saveAccount(testAccountDto);
        verify(accountRepository).findByUserId(anyString());
        verify(accountRepository).save(any());
    }

    @Test
    void saveAccount_alreadyWithdrawn() {
        when(accountRepository.findByUserId("user")).thenReturn(Optional.of(mockAccountWithdrawn));
        when(accountRepository.save(any())).thenReturn(any(Account.class));

        accountService.saveAccount(testAccountDto);

        verify(accountRepository).findByUserId(anyString());
        verify(accountRepository).save(any());
    }

    @Test
    void saveAccount_alreadyExistingAccount() {
        Account mockAccountRegistered = new Account("user", "123", "example@naver.com", UserStatus.INACTIVE);
        when(accountRepository.findByUserId("user")).thenReturn(Optional.of(mockAccountRegistered));
        assertThrows(AccountAlreadyExistException.class, () -> accountService.saveAccount(testAccountDto));
        verify(accountRepository).findByUserId(anyString());
    }

    @Test
    void getAccount_string() {
        when(accountRepository.findByUserId(anyString())).thenReturn(Optional.of(mockAccountActive));
        Account account = accountService.getAccount("user");
        assertEquals(mockAccountActive, account);
        verify(accountRepository).findByUserId(anyString());
    }

    @Test
    void getAccount_stringNotFound() {
        when(accountRepository.findByUserId(anyString())).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount("user"));
        verify(accountRepository).findByUserId(anyString());
    }

    @Test
    void getAccount_stringWithdrawn() {
        when(accountRepository.findByUserId("user")).thenReturn(Optional.of(mockAccountWithdrawn));
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount("user"));
        verify(accountRepository).findByUserId("user");
    }

    @Test
    void getAccount_long() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(mockAccountActive));
        Account account = accountService.getAccount(1L);
        assertEquals(mockAccountActive, account);
        verify(accountRepository).findById(anyLong());
    }

    @Test
    void getAccount_longNotFound() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(1L));
        verify(accountRepository).findById(anyLong());
    }

    @Test
    void getAccount_longWithdrawn() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(mockAccountWithdrawn));
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccount(1L));
        verify(accountRepository).findById(anyLong());
    }

    @Test
    void updateStatus() {
        AccountStatusDto statusDto = new AccountStatusDto(UserStatus.WITHDRAWN);
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(mockAccountActive));
        Account account = accountService.updateStatus(1L, statusDto);
        assertEquals(UserStatus.WITHDRAWN, account.getStatus());
    }

    @Test
    void updateStatus_notFound() {
        AccountStatusDto statusDto = new AccountStatusDto(UserStatus.WITHDRAWN);
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.updateStatus(1L, statusDto));
    }
}
