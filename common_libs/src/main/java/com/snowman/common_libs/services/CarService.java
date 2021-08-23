package com.snowman.common_libs.services;

import com.snowman.common_libs.domain.Car;


public interface CarService {
    Car createCar(String carMark, String carModel, int carYear, int carPower, int carPrice);
    void saveCar(Car car);
}
