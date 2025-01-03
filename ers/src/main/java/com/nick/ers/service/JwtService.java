package com.nick.ers.service;

import java.security.Key;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.nick.ers.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//token generator and parsing token
@Service
public class JwtService {
    
    @Value("${jwt.secret}")
    private String secretKey;

    // private String secretKey = " ";

    // public JwtService(@Value("${jwt.secret}") String configuredSecretKey){
    //    this.secretKey = configuredSecretKey;
    // }

    public String generateToken(User user) {

       

        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getUserId());
        claims.put("firstname", user.getFirstname());
        claims.put("lastname", user.getLastname());
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 *60 * 60 * 1000))
                .signWith(getKey())
                .compact();
        }
                
        private Key getKey() {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        }

        public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
            Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }
    
     
        private Claims extractAllClaims(String token) {
            return Jwts.parserBuilder() 
                    .setSigningKey(getKey()) 
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
    
   
        public String extractUsername(String token) {
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            token = token.trim();
            return extractClaims(token, Claims::getSubject);
        }

        public List<String> extractRoles(String token) {
            Claims claims = extractAllClaims(token);
            List<String> roles = claims.get("role", List.class);
            return roles != null ? roles : new ArrayList<>();
        }

        public boolean validtoken(String parsedtoken){
            Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parse(parsedtoken);

            return true;
        }

}
