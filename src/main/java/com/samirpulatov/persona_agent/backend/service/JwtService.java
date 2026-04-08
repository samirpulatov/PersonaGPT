package com.samirpulatov.persona_agent.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    public static final String SECRET = "MqVC76FWb9dbJFOYdY462GiIeieDsKv8GEdgE399wJN";

    //Use email as the subject of the token
    public String generateToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(); // claims are statements, typically about the user
        claims.put("userId",userDetails.getId());
        claims.put("accountType",userDetails.getAccountType());
        claims.put("username",userDetails.getUsername());
        claims.put("role",userDetails.getRole());
        return createToken(claims,userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000 * 60 * 30)) // 1000 ms = 1 sec, 60 sec = 1 min, 30 min = 30 min => 1*1*30 = 30 min
                .signWith(getSignKey())
                .compact();
    }

    //get the secret key via parsing the SECRET string into a byte array and then using the Keys.hmacShaKeyFor() method to create a SecretKey object
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //Method to get the user email from the token
    public String getUserEmailFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Method to check if the token is expired
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //One common method to extract claims from a token and apply a function to them (like getting the username or expiration date)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //method to extract all claims from a token and return them as a Claims object
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //Method to check if the token is valid (not expired)
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //Get the user email from the token and check if it matches the user email in the userDetails object
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userEmail = getUserEmailFromToken(token);
        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
