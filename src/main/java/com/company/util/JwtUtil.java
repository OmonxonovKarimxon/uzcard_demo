package com.company.util;

import com.company.dto.JwtDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    private static final String secretKey = "someKeyWord";

    public static String encode(JwtDTO dto) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date()); // 18:58:00
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000))); // 19:58:00
        jwtBuilder.setIssuer("Mazgi production");
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.claim("id", dto.getId());
        jwtBuilder.claim("role", dto.getRole());
        jwtBuilder.claim("password", dto.getPassword());
        jwtBuilder.claim("username", dto.getUsername());
        jwtBuilder.claim("status", dto.getStatus());
        jwtBuilder.claim("visible", dto.getVisible());

        String jwt = jwtBuilder.compact();
        return jwt;
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        JwtDTO dto =  new JwtDTO();
        dto.setId((String) claims.get("id"));
        dto.setRole((String) claims.get("role"));
        dto.setStatus((String) claims.get("status"));
        dto.setVisible((Boolean) claims.get("visible"));
        dto.setPassword((String) claims.get("password"));
        dto.setUsername((String) claims.get("username"));
        return dto;
    }





}
