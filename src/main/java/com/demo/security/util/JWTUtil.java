package com.demo.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JWTUtil {
    private static final String SECRET="my-super-secret-which-is-long-enough-no-one-can-steal-and-crack-my-super-secret-which-is-long-enough-no-one-can-steal";
    //private static final SecretKey key= Keys.hmacShaKeyFor(SECRET.getBytes());
    private static final long EXPIRATION=1000*60*60;
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        final String username=extractUserName(token);
        return userDetails.getUsername().equals(username) && !isTokenExpired(token);
    }

    public String extractUserName(String token){
        return extractClaims(token,Claims::getSubject);
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver ){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
