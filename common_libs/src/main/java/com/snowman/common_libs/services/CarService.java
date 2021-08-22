package com.snowman.common_libs.services;

import com.snowman.common_libs.domain.Car;
import org.springframework.stereotype.Service;

@Service
public interface CarService {
    Car createCar(String carMark, String carModel, String carYear, String carPower, String carPrice);
    void saveCar(Car car);
}
