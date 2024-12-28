package com.alten.shop.services;

import com.alten.shop.dtos.AccountDTO;
import com.alten.shop.dtos.requests.ProductDTO;
import com.alten.shop.dtos.requests.UserDTO;
import com.alten.shop.entities.Account;
import com.alten.shop.repositories.AccountRepository;
import com.alten.shop.services.impl.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @InjectMocks
    AccountService accountService;
    @Mock
    AccountRepository accountRepository;
    @Mock
    ObjectMapper objectMapper;


    @Test
    void  findAccountByEmailTest() {
        given(accountRepository.findAccountByEmail("Test@test.com")).willReturn(buildAccount());
        given(objectMapper.convertValue(buildAccount(), AccountDTO.class)).willReturn(buildAccountDTO());
        AccountDTO accountDTO=accountService.findAccountByEmail("Test@test.com");
        Assertions.assertNotNull(accountDTO);
        Assertions.assertEquals("Test@test.com",accountDTO.getEmail());
        Assertions.assertEquals(1L,accountDTO.getId());
        Assertions.assertEquals("123654",accountDTO.getPassword());
        Assertions.assertEquals("username",accountDTO.getUsername());
        Assertions.assertEquals("FName",accountDTO.getFirstname());
    }
    @Test
    void findAccountByUsernameTest() {
        given(accountRepository.findAccountByUsername("username")).willReturn(buildAccount());
        given(objectMapper.convertValue(buildAccount(), AccountDTO.class)).willReturn(buildAccountDTO());
        AccountDTO accountDTO=accountService.findAccountByUsername("username");
        Assertions.assertNotNull(accountDTO);
        Assertions.assertEquals("Test@test.com",accountDTO.getEmail());
        Assertions.assertEquals(1L,accountDTO.getId());
        Assertions.assertEquals("123654",accountDTO.getPassword());
        Assertions.assertEquals("username",accountDTO.getUsername());
        Assertions.assertEquals("FName",accountDTO.getFirstname());
    }

    @Test
    void createAccount() {
        given(objectMapper.convertValue(buildAccountDTO(),Account.class)).willReturn(buildAccount());
        given(accountRepository.save(buildAccount())).willReturn(buildAccount());
        given(objectMapper.convertValue(buildAccount(),AccountDTO.class)).willReturn(buildAccountDTO());
        AccountDTO accountDTO=accountService.createAccount(buildAccountDTO());
        Assertions.assertNotNull(accountDTO);
        Assertions.assertEquals(accountDTO.getEmail(),buildAccountDTO().getEmail());
        Assertions.assertEquals(accountDTO.getUsername(),buildAccountDTO().getUsername());
        Assertions.assertEquals(accountDTO.getFirstname(),buildAccountDTO().getFirstname());
        Assertions.assertNotNull(accountDTO.getId());
    }

    private AccountDTO buildAccountDTO(){
        AccountDTO accountDTO=new AccountDTO();
        accountDTO.setId(1L);
        accountDTO.setEmail("Test@test.com");
        accountDTO.setPassword("123654");
        accountDTO.setUsername("username");
        accountDTO.setFirstname("FName");
        return accountDTO;
    }

    private Account buildAccount(){

        Account account=new Account();
        account.setEmail("Test@test.com");
        account.setPassword("123654");
        account.setUsername("username");
        account.setFirstname("FName");
        return account;
    }
}