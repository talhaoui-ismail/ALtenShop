package com.alten.shop.auth;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Date;
@Configuration
public class JwtTokenUtil {
    private static final String SECRET_KEY = "QWERTYUIOPASDFGHJKLMNBCZZQWERTYUIOPASDFGHJKLMNBCZZQWERTYUIOPASDFGHJKLMNBCZZ";
    private static final long EXPIRATION_TIME = 1_000L * 60 * 60;
    private final SecretKey signingKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(signingKey )
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token).getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String getEmailFrom(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parse(token)
                .accept(Jws.CLAIMS)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, String email) {
        return email.equals(getEmailFrom(token)) && !isTokenExpired(token);
    }
}