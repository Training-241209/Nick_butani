package com.ers.employee_reimbursement_system.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ers.employee_reimbursement_system.entity.User;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import com.ers.employee_reimbursement_system.DAOs.*;

@Component
public class JwtTokenUtil {
    
    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(User user) {
      
        return Jwts.builder()
                .claim("email", user.getEmail())
                .claim("username", user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) 
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        return new javax.crypto.spec.SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public User decodeToken(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new User(claims.get("id", Long.class), claims.get("email", String.class));
    }
}
