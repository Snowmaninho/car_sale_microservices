package com.snowman.common_libs.services;

import com.snowman.common_libs.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User getUser(long id);
    void saveUser(User user);
}
