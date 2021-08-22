package com.snowman.common_libs.services;

import com.snowman.common_libs.domain.User;
import com.snowman.common_libs.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User getUser(long id) {
        return userRepo.getById(id);
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }
}
