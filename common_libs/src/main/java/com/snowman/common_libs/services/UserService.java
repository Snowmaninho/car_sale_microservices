package com.snowman.common_libs.services;

import com.snowman.common_libs.domain.AppUser;

import java.util.List;

public interface UserService {

    AppUser register(AppUser appUser);

    List<AppUser> getAll();

    AppUser findByUsername(String username);

    AppUser findById(Long id);

    AppUser createUser(String username, String firstName, String lastname, String email, String password);

    void delete(Long id);
}
