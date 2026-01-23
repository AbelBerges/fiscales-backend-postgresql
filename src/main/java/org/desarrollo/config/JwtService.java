package org.desarrollo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private static final String SECRET_KEY = "aC6lFmCwI1j0/BGqDV+DXVKxSOqVvx0ORhobp6OmsbU1Ol4awS8MOWLAQEuofMfcvflo+ZVN08DBVCRz7AzWPw==";

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generarToken(Integer idUsuario, Integer idBasica, String rol) {
        return Jwts.builder()
                .setSubject(String.valueOf(idUsuario))
                .claim("rol", rol)
                .claim("basica", idBasica)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String obtenerUsuario(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean tokenValido(String token, String userName) {
        return obtenerUsuario(token).equals(userName);
    }

}
