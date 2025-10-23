package com.authService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Main agenda of the service:
 * To create the access token on login or refresh api hits
 * To verify if jwt is expired or not
 * Extracts username and expiryToken

 * In short - generateToken and verifyToken / validateToken

 */

@Service // bean + business logic
public class JWTService {

    // loaded from env
    @Value("${jwt.secret}")
    private static String JWTSecret;

    // key used to sign the JWT token
    public Key getSignKey() {
        byte[] bytes = Decoders.BASE64.decode(JWTSecret); // converting the token into bytes array so that new key instance is generated
        return Keys.hmacShaKeyFor(bytes);
    }

    // generate token
    public String generateToken(Map<String, Object> claims, String username) {
        // Map<String, Object> claims = new HashMap<>(); -> good for early setup only but if we want to add email in future claims it better as a param
        return createToken(claims, username);
    }

    public String createToken(Map<String, Object> claims, String username) {
        /* Deprecated
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))
                    .signWith(getSignKey(), SignatureAlgorithm.HS256)
                    .compact();
         */

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(getSignKey()) // no need to specify algo like Jwts.SIG.HS256
                .compact();
    }

    public Claims extractAllClaims(String token) {
        /*
            Deprecated

            return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        */

        // verify the jwt with the secret key used to sign the jwt and then parse it to receive the claims (payload)
        return Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // extract the username from the subject present in the claims (payload)
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // extract the expiryDate from the claims
    public Date extractExpiryDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // validating / verifying token
    // done by checking if the JWT access token is expired? and if is it valid cryptographically using the key?

    // check if token expired
    public Boolean isTokenExpired(String token) {
        return extractExpiryDate(token).before(new Date());
    }

    // CustomUserDetails will store the logged-in user from the database and is passed here
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
