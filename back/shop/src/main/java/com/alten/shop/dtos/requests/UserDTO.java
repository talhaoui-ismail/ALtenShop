package com.alten.shop.dtos.requests;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private String email;
    private String password;
}
