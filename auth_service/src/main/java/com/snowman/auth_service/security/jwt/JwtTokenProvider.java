package com.snowman.auth_service.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;


// создаём класс для генерации токена
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds; // сколько в миллисекундах будет валиден данный токен

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        Base64.getEncoder().encodeToString(secret.getBytes());
    }

    // генерируем токен на основе имени пользователя и его ролей
    public String createToken(String username, String role) { // можно передавать UserDetails и вытаскивать данные из него

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(role));

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secret)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        return auth;
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(Cookie[] cookies) {

        try {
            if( cookies == null || cookies.length < 1 ) {
                throw new AuthenticationServiceException( "Invalid Token" );
            }

            Cookie sessionCookie = null;
            for( Cookie cookie : cookies ) {
                if( ( "JwtAuthTokenInCookie" ).equals( cookie.getName() ) ) {
                    sessionCookie = cookie;
                    break;
                }
            }

            String bearerToken = sessionCookie.getValue();
            if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
                return bearerToken.substring(7, bearerToken.length());
            }
        } catch (Exception e) {
        }
        return null;
    }

    // используем Claims, класс реализованный в библиотеке JsonWebToken
    // для шифрования и дешифровки токена необходим "секрет" - прописывается в application.properties
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            // Если expiration < сегодняшней даты = токен не валиден
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
//            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
        return false;
    }

    private List<String> getRoleNames(String userRoles) {
        return Collections.singletonList(userRoles);
    }
}
