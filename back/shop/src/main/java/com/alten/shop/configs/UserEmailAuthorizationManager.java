package com.alten.shop.configs;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class UserEmailAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final  String ALLOWED_EMAIL = "admin@admin.com";

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Authentication auth = authentication.get();

        // Ensure that the authentication and principal (user) are not null
        if (auth == null || auth.getPrincipal() == null) {
            return new AuthorizationDecision(false); // Deny if no authentication is found
        }

        // Assuming the principal is an instance of User with the email as the username
        String userEmail = ((User) auth.getPrincipal()).getUsername();

        // Check if the user's email matches the allowed one
        if (ALLOWED_EMAIL.equals(userEmail)) {
            return new AuthorizationDecision(true); // Allow access if the email matches
        }

        return new AuthorizationDecision(false);
    }
}