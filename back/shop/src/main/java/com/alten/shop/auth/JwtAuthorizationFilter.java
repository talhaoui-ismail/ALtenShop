package com.alten.shop.auth;

import com.alten.shop.dtos.AccountDTO;
import com.alten.shop.services.IAccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final IAccountService accountService;
    @Autowired
    public JwtAuthorizationFilter(JwtTokenUtil jwtTokenUtil, IAccountService accountService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.accountService = accountService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            String email = jwtTokenUtil.getEmailFrom(jwt);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                AccountDTO account = accountService.findAccountByEmail(email);
                if (account != null && jwtTokenUtil.validateToken(jwt, account.getEmail())) {
                    UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(account, null, null);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
