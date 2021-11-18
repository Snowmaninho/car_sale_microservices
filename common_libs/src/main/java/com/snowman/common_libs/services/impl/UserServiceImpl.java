package com.snowman.common_libs.services.impl;

import com.snowman.common_libs.domain.AppUser;
import com.snowman.common_libs.domain.Status;
import com.snowman.common_libs.repos.UserRepo;
import com.snowman.common_libs.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser registerUser(AppUser appUser) {

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setStatus(Status.ACTIVE);
        AppUser registeredAppUser = userRepo.save(appUser);
        log.info("IN registerUser - user: {} successfully registered", registeredAppUser);

        return registeredAppUser;
    }

    @Override
    public List<AppUser> getAllUsers() {
        List<AppUser> result = userRepo.findAll();
        log.info("IN getAllUsers - {} users found", result.size());
        return result;
    }

    @Override
    public AppUser findUserByUsername(String username) {
        AppUser result = userRepo.findByUsername(username);
        if (result != null) {
            log.info("IN findUserByUsername - user: {} found by username: {}", result, username);
        } else {
            log.warn("User with username: {} not found", username);
        }
        return result;
    }

    @Override
    public AppUser findUserByEmail(String email) {
        AppUser result = userRepo.findByEmail(email);
        log.info("IN findUserByEmail - user: {} found by email: {}", result, email);
        return result;
    }

    @Override
    public AppUser findUserById(Long id) {
        AppUser result = userRepo.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

    @Override
    public AppUser createUser(String username, String firstName, String lastname, String email, String password) {
        AppUser result = new AppUser(username, firstName, lastname, email, password);
        log.info("IN createUser - user: {} created with id: {}", result, result.getId());
        return result;
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
        log.info("IN deleteUser - user with id: {} successfully deleted", id);
    }
}
