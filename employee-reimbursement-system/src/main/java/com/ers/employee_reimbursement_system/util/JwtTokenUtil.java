package com.ers.employee_reimbursement_system.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class JwtTokenUtil {
    private String SECRET_KEY = "646a0cbdbdf769ccea705811e9dddd8f0600f9dec3b7b3711a34df41dccf0b5df3c633a9e2fd92d011d8deba7d6052ae21ef351478e81afcb8489a89c2afeb7203d275aec240a00853aa9ab2a7373823d96e70a2122f8700547c3803b331aa9a96e0577a015d9c029824b881dd7ac8d9b4c07f750a84073a564f45441589715d7d48070199d0ffbdf8dba9a56158fb2e944b1e041928cfcc6065de296c4bb511fa90c9c06721bc4bfc09fb017969e9be328d99a5e086e4746e3b3499c516c45b81b8bcc78ad63e099db9c99bd94e7a475e2546d51a50e00414c8efab9b5b0b5b660e2cb65e872af6e4dab454475d28c0f3ed7ec632e207ff9c4ddef8aa51afa6";

    @SuppressWarnings("deprecation")
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) 
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    @SuppressWarnings("deprecation")
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @SuppressWarnings("deprecation")
    public Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}