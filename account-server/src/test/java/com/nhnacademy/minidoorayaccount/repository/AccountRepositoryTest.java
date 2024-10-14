package com.nhnacademy.minidoorayaccount.repository;

import com.nhnacademy.minidoorayaccount.entity.Account;
import com.nhnacademy.minidoorayaccount.entity.enums.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Sql("account-sample.sql")
    @Test
    void findByAccountId() {
        Account account = accountRepository.findById(1L).orElse(null);

        assertNotNull(account);
        assertEquals("12345", account.getPassword());
        assertEquals("user1", account.getUserId());
        assertEquals("example@naver.com", account.getEmail());
        assertEquals(UserStatus.REGISTERED, account.getStatus());
    }

    @Sql("account-sample.sql")
    @Test
    void findByAccountId_notFound() {
        Account account = accountRepository.findById(1024L).orElse(null);

        assertNull(account);
    }

    @Sql("account-sample.sql")
    @Test
    void findByUserId() {
        Account account = accountRepository.findByUserId("user1").orElse(null);

        assertNotNull(account);
        assertEquals("12345", account.getPassword());
        assertEquals("user1", account.getUserId());
        assertEquals("example@naver.com", account.getEmail());
        assertEquals(UserStatus.REGISTERED, account.getStatus());
    }

    @Sql("account-sample.sql")
    @Test
    void findByUserId_notFound() {
        Account account = accountRepository.findByUserId("userX").orElse(null);

        assertNull(account);
    }

    @Test
    @Sql("account-sample.sql")
    void findById() {
        Account account = accountRepository.findById(3L).orElse(null);

        assertNotNull(account);
        assertEquals("12345", account.getPassword());
        assertEquals("user3", account.getUserId());
        assertEquals("example3@naver.com", account.getEmail());
        assertEquals(UserStatus.REGISTERED, account.getStatus());
    }

    @Test
    void saveAccount() {
        Account account = new Account("userx", "12345", "example@naver.com", UserStatus.REGISTERED);
        Account savedAccount = accountRepository.save(account);
        assertNotNull(savedAccount);
    }

    @Test
    void updateAccountStatus() {
        Account account = new Account("userx", "12345", "example@naver.com", UserStatus.REGISTERED);
        Account savedAccount = accountRepository.save(account);
        assertNotNull(savedAccount);
    }
}
