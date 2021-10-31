package com.snowman.main_auto_service.security.jwt;


import com.snowman.common_libs.domain.AppUser;
import com.snowman.common_libs.domain.Status;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                mapToGrantedAuthorities(new ArrayList<>(Collections.singletonList(user.getRole()))), // конвертация ролей в GrantedAuthorities
                user.getStatus().equals(Status.ACTIVE),  // enabled будет только в том случае, если его статус - ACTIVE
                user.getUpdated() // последний раз обновлённый пароль в последней дате обновления User
        );
    }

    // метод конвертирующий наши роли в GrantedAuthorities
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> userRoles) {
        return userRoles.stream()
                .map(SimpleGrantedAuthority::new
                ).collect(Collectors.toList());
    }
}

