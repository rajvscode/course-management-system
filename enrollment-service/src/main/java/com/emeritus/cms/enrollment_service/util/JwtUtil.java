package com.emeritus.cms.enrollment_service.util;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // @Value("${jwt.secret}")
    private String SECRET = "997D98819223A2CCD32F08DD6C983B27DF1C92E08B8125C0C9AA70A32D42D38E";

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Collection<GrantedAuthority> getAuthorities(String token) {

        final Claims claims = extractAllClaims(token);
        String roles = claims.get("roles", String.class);
        Collection<GrantedAuthority> authorities = Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return authorities;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Long getUserIdFromToken(String token) {
        final Claims claims = extractAllClaims(token);
        String userId = claims.get("userId", String.class);
        return Long.parseLong(userId);
    }
}

