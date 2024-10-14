package com.nhnacademy.taskapi.repository;

import com.nhnacademy.taskapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
