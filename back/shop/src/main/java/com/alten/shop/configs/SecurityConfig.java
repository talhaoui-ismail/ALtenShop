package com.alten.shop.configs;

import com.alten.shop.auth.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class SecurityConfig {
    private static final String PRODUCT_URL="/api/v1/products";
    private static final  String ACCOUNT_URL="/api/v1/accounts";

    UserEmailAuthorizationManager userEmailAuthorizationManager ;

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter,UserEmailAuthorizationManager userEmailAuthorizationManager) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.userEmailAuthorizationManager=userEmailAuthorizationManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).
                headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher(PRODUCT_URL, "GET"),
                                new AntPathRequestMatcher(PRODUCT_URL.concat("/**"), "GET"),
                                new AntPathRequestMatcher(ACCOUNT_URL.concat("/token"),"POST"),
                                new AntPathRequestMatcher(ACCOUNT_URL,"POST"),
                                new AntPathRequestMatcher("/h2-console"),
                                new AntPathRequestMatcher("/h2-console/**"),
                                new AntPathRequestMatcher("/h2-console/login.do/**")
                                ,new AntPathRequestMatcher("/swagger-ui.html/**")
                                , new AntPathRequestMatcher("/swagger-ui/index.html")
                                ,new AntPathRequestMatcher("/swagger-ui/**")
                                ,new AntPathRequestMatcher("/v3/**")

                                )
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}