package com.snowman.common_libs.services;

import com.snowman.common_libs.domain.AppUser;

import java.util.List;

public interface UserService {

    AppUser registerUser(AppUser appUser);

    List<AppUser> getAllUsers();

    AppUser findUserByUsername(String username);
    AppUser findUserByEmail(String email);

    AppUser findUserById(Long id);

    AppUser createUser(String username, String firstName, String lastname, String email, String password);

    void deleteUser(Long id);
}
