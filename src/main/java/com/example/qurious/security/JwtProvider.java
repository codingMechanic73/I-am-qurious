package com.example.qurious.security;

import com.example.qurious.exception.InvalidJwtAuthenticationException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * JWT Provider which provides utilities to generate and validate tokens
 */
@Data
@Service
public class JwtProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKeyText;

    @Value("${security.jwt.token.validity}")
    private long jwtTokenvalidityInMilliseconds;

    private SecretKey secretEncodedKey;

    /**
     * encode the secret key which will be used to sign the token
     */
    @PostConstruct
    public void init() {
        secretEncodedKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secretKeyText.getBytes()));
    }

    /**
     * Generate token based on userName as subject
     *
     * @param userName the subject of the token
     * @return token in the form of string
     */
    public String generateToken(String userName) {

        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + jwtTokenvalidityInMilliseconds);

        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(secretEncodedKey)
                .compact();
    }

    /**
     * Validates the token and returns the subject
     *
     * @param authToken token
     * @return userName or subject
     * @throws InvalidJwtAuthenticationException if token is not valid or has expired
     */
    public String validateTokenAndGetUserName(String authToken)
            throws InvalidJwtAuthenticationException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretEncodedKey)
                    .build()
                    .parseClaimsJws(authToken).getBody().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }

}
