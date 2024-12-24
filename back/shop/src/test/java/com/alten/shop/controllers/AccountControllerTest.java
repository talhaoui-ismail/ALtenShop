package com.alten.shop.controllers;

import com.alten.shop.dtos.AccountDTO;
import com.alten.shop.dtos.requests.UserDTO;
import com.alten.shop.services.impl.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountControllerTest {
    private final AccountService accountService;
    private final ObjectMapper objectMapper;
    private final TestRestTemplate restTemplate;
    private String jwt;
    @Autowired
    public AccountControllerTest(AccountService accountService, ObjectMapper objectMapper, TestRestTemplate restTemplate) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }


    @BeforeEach
    public void getJWT() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request=new HttpEntity<>(objectMapper.writeValueAsString(buildUserDTO()),headers);
        ResponseEntity<String> syntheseResponse =restTemplate.postForEntity("/api/v1/accounts/token", request, String.class);
        this.jwt=syntheseResponse.getBody();
    }
    @Test
    @Order(1)
    void createAccountTest() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION,"Bearer ".concat(jwt));
        HttpEntity<String> request=new HttpEntity<>(objectMapper.writeValueAsString(buildAccountDTO()),headers);
        ResponseEntity<String> syntheseResponse =restTemplate.postForEntity("/api/v1/accounts", request, String.class);
        Assertions.assertEquals(HttpStatusCode.valueOf(200),syntheseResponse.getStatusCode());
        Assertions.assertNotNull(syntheseResponse.getBody());
    }

    @Test
    @Order(2)
    void authenticateTest() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request=new HttpEntity<>(objectMapper.writeValueAsString(buildUserDTO()),headers);
        ResponseEntity<String> listResponseEntity=restTemplate.exchange("/api/v1/accounts/token",HttpMethod.POST,request,String.class);
        Assertions.assertEquals(HttpStatusCode.valueOf(200),listResponseEntity.getStatusCode());
    }

    private AccountDTO buildAccountDTO(){

        AccountDTO accountDTO=new AccountDTO();
        accountDTO.setEmail("Test@test.com");
        accountDTO.setPassword("123654");
        accountDTO.setUsername("username");
        accountDTO.setFirstname("FName");
        return accountDTO;
    }
    private UserDTO buildUserDTO(){
        UserDTO userDTO=new UserDTO();
        userDTO.setEmail("admin@admin.com");
        userDTO.setPassword("123654");
        return userDTO;
    }
}
