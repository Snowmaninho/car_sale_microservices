package com.snowman.common_libs.services;

import com.snowman.common_libs.domain.User;


public interface UserService {
    User getUser(long id);
    void saveUser(User user);
}
