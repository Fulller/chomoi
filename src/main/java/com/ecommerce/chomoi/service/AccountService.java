package com.ecommerce.chomoi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.chomoi.entities.Account;
import com.ecommerce.chomoi.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Account> getAccountById(String accountId) {
        return accountRepository.findById(accountId);
    }
}
