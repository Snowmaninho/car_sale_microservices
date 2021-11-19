package com.snowman.auth_service.security.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

// create a class for generating a token
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;  // our token secret

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds; // how many milliseconds this token will be valid

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        Base64.getEncoder().encodeToString(secret.getBytes());
    }

    // generate a token based on username and roles
    public String createToken(String username, String role) {
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
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        return authentication;
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(Cookie cookie) {

            String bearerToken = cookie.getValue();
            if (bearerToken != null) {
                if (bearerToken.startsWith("Bearer_")) {
                    return bearerToken.substring(7, bearerToken.length());
                }
            }

        return null;
    }

    // use Claims, a class implemented in the JsonWebToken library
    // to encrypt and decrypt the token, a "secret" is required - it is written in application.properties
    public Boolean validateToken(String token) {

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            // If expiration < today's date = token is not valid
            if (claims.getBody().getExpiration().before(new Date())) {
                return Boolean.FALSE;
            }

            return Boolean.TRUE;
        } catch (JwtException | IllegalArgumentException e) {
            return Boolean.FALSE;
        }
    }

    private List<String> getRoleNames(String userRoles) {
        return Collections.singletonList(userRoles);
    }
}
