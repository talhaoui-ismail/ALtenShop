package com.alten.shop.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true, nullable = false)
    private String username;
    private String firstname;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    // creation date
    // last login
    // lst modification
}