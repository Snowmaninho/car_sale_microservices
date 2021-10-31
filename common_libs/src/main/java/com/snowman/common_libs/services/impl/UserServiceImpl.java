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
    public AppUser register(AppUser appUser) {

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        appUser.setStatus(Status.ACTIVE);

        AppUser registeredAppUser = userRepo.save(appUser);

        log.info("IN register - user: {} successfully registered", registeredAppUser);

        return registeredAppUser;
    }

    @Override
    public List<AppUser> getAll() {
        List<AppUser> result = userRepo.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    @Override
    public AppUser findByUsername(String username) {
        AppUser result = userRepo.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public AppUser findById(Long id) {
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
        return new AppUser(username, firstName, lastname, email, password);
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
