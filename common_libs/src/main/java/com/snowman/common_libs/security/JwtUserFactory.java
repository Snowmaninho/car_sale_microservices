package com.snowman.common_libs.security;


import com.snowman.common_libs.domain.AppUser;
import com.snowman.common_libs.domain.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// Simple factory fo "Jwt user"
public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(AppUser user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(Collections.singletonList(user.getRole()))), // converting roles to GrantedAuthorities
                user.getStatus().equals(Status.ACTIVE),  // enabled will be only if its status is ACTIVE (here we activate user)
                user.getUpdated() // the last time the password was updated in the last date of the User update
        );
    }

    // method converting our roles into GrantedAuthorities
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> userRoles) {
        return userRoles.stream()
                .map(SimpleGrantedAuthority::new
                ).collect(Collectors.toList());
    }
}

