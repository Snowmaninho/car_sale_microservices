package com.snowman.common_libs.repos;

import com.snowman.common_libs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
