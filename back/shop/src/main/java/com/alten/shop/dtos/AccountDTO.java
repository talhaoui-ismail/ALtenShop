package com.alten.shop.dtos;

import lombok.Data;

import java.io.Serializable;
@Data
public class AccountDTO implements Serializable {
    private Long id;
    private String username;
    private String firstname;
    private String email;
    private String password;
}
