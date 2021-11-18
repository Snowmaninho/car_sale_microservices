package com.snowman.common_libs.repos;

import com.snowman.common_libs.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String name);
    AppUser findByEmail(String email);

}
