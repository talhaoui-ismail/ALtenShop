package com.alten.shop.api;

import com.alten.shop.dtos.AccountDTO;
import com.alten.shop.dtos.requests.UserDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/accounts")
public interface IAccountAPI {
    /**
     * TO DO : a revoir Get with body ?
     * @param email
     * @return
     */

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> authenticate(@Validated @RequestBody UserDTO userDTO);
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> createAccount(@Validated @RequestBody AccountDTO accountDTO);

}