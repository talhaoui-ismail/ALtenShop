package com.alten.shop.services;

import com.alten.shop.dtos.AccountDTO;

public interface IAccountService {
    AccountDTO findAccountByEmail(String email);

    AccountDTO findAccountByUsername(String username);

    AccountDTO createAccount(AccountDTO accountDTO);
}
