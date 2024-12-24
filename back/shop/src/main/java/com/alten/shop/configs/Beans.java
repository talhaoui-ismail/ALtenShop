package com.alten.shop.configs;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Beans {

        @Bean
        ObjectMapper getObjectMapper(){
            ObjectMapper objectMapper=new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
            return objectMapper;
        }
        @Bean
    PasswordEncoder getPasswordEncoder(){
            return  new BCryptPasswordEncoder();
        }
}