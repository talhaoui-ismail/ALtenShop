package com.alten.shop.services.impl;

import com.alten.shop.dtos.AccountDTO;
import com.alten.shop.entities.Account;
import com.alten.shop.repositories.AccountRepository;
import com.alten.shop.services.IAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {

    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;
    @Autowired
    public AccountService(AccountRepository accountRepository,ObjectMapper objectMapper) {
        this.objectMapper=objectMapper;
        this.accountRepository = accountRepository;
    }

    public AccountDTO findAccountByEmail(String email) {
        AccountDTO accountDTO= objectMapper.convertValue(accountRepository.findAccountByEmail(email),AccountDTO.class);
        return accountDTO;


    }

    public AccountDTO findAccountByUsername(String username) {
        return objectMapper.convertValue(accountRepository.findAccountByUsername(username),AccountDTO.class);
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account=objectMapper.convertValue(accountDTO,Account.class);
        return objectMapper.convertValue(accountRepository.save(account),AccountDTO.class);
    }
}
