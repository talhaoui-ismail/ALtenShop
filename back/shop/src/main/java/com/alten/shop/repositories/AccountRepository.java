package com.alten.shop.repositories;

import com.alten.shop.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query("from Account where email=:email")
    Account findAccountByEmail(@Param("email") String email);
    @Query("from Account where username=:username")
    Account findAccountByUsername(@Param("username") String username);
}
