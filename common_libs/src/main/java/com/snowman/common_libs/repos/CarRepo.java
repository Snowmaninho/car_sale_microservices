package com.snowman.common_libs.repos;

import com.snowman.common_libs.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepo extends JpaRepository<Car, Long> {
}
