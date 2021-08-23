package com.snowman.common_libs.services;

import com.snowman.common_libs.domain.Car;
import com.snowman.common_libs.repos.CarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    private CarRepo carRepo;

    @Autowired
    public CarServiceImpl(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    @Override
    public Car createCar(String carMake, String carModel, int carYear, int carPower, int carPrice) {
        return new Car().setCarMake(carMake).setCarModel(carModel).setCarYear(carYear).setCarPower(carPower).setCarPrice(carPrice);
    }

    @Override
    public void saveCar(Car car) {
        carRepo.save(car);
    }
}
