package com.alten.shop.controllers;

import com.alten.shop.api.IAccountAPI;
import com.alten.shop.auth.JwtTokenUtil;
import com.alten.shop.dtos.AccountDTO;
import com.alten.shop.dtos.requests.UserDTO;
import com.alten.shop.services.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements IAccountAPI {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AccountController(AccountService accountService, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public ResponseEntity<String> createAccount(AccountDTO account) {
        if (accountService.findAccountByEmail(account.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already in use!");
        }
        // a verifier in CDC apres
        if (accountService.findAccountByUsername(account.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Username already in use!");
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        AccountDTO createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok("Account " + createdAccount.getEmail() + " created successfully");
    }
    public ResponseEntity<String> authenticate(UserDTO userDTO) {
        AccountDTO storedAccount = accountService.findAccountByEmail(userDTO.getEmail());
        if (storedAccount != null && passwordEncoder.matches(userDTO.getPassword(), storedAccount.getPassword())) {
            String token = jwtTokenUtil.generateToken(storedAccount.getEmail());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password!");
    }
}
