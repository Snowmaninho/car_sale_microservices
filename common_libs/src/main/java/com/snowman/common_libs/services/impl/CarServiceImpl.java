package com.snowman.common_libs.services.impl;

import com.snowman.common_libs.domain.Car;
import com.snowman.common_libs.repos.CarRepo;
import com.snowman.common_libs.services.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CarServiceImpl implements CarService {

    private final CarRepo carRepo;

    @Autowired
    public CarServiceImpl(CarRepo carRepo) {
        this.carRepo = carRepo;
    }

    @Override
    public Car createCar(String carMake, String carModel, int carYear, int carPower, int carPrice) {
        Car result = new Car().setCarMake(carMake).setCarModel(carModel).setCarYear(carYear).setCarPower(carPower).setCarPrice(carPrice);
        log.info("IN createCar - created car with: make - {}, model - {}, year - {}, power - {}, price - {}",
                result.getCarMake(), result.getCarModel(), result.getCarYear(), result.getCarPower(), result.getCarPrice());
        return result;
    }

    @Override
    public void saveCar(Car car) {
        carRepo.save(car);
        log.info("IN saveCar - car saved with: make - {}, model - {}, year - {}, power - {}, price - {}",
                car.getCarMake(), car.getCarModel(), car.getCarYear(), car.getCarPower(), car.getCarPrice());
    }
}
