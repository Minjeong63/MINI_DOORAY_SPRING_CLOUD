package com.nhnacademy.taskapi.service;

import com.nhnacademy.taskapi.entity.Account;
import com.nhnacademy.taskapi.exception.ResourceAlreadyExistException;
import com.nhnacademy.taskapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void addAccount(Long accountId){
        if(accountRepository.existsById(accountId)){
            throw new ResourceAlreadyExistException("AccountId: " +accountId+ " already exist");
        }
        accountRepository.save(new Account(accountId));
    }
}
