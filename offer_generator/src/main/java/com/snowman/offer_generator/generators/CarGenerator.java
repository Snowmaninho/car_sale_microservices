package com.snowman.offer_generator.generators;

import com.snowman.common_libs.domain.Car;
import com.snowman.common_libs.services.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

// generate Car
@Component
@Slf4j
public class CarGenerator {

    private final Map<String, List<String>> makeAndModels;
    private final Random random;
    private final int yearDiff;
    private final int carMinPrice;
    private final int carMaxPrice;
    private final int carMinPower;
    private final int carMaxPower;

    private final CarService carService;

    @Autowired
    public CarGenerator(CarService carService) {
        this.carService = carService;
    }

    {
        makeAndModels = makeAndModels();
        random = new Random();
        yearDiff = 20;
        carMinPrice = 1_000_000;
        carMaxPrice = 6_000_000;
        carMinPower = 120;
        carMaxPower = 350;
    }

    private List<String> createBMWList() {
        return Arrays.asList("M5", "M3", "X5", "X3", "X7");
    }

    private List<String> createToyotaList() {
        return Arrays.asList("Corolla", "Camry", "RAV4", "Land Cruiser", "Highlander");
    }

    private List<String> createNissanList() {
        return Arrays.asList("Qashqai", "X-Trail", "Almera", "Murano", "GT-R");
    }

    private List<String> createSkodaList() {
        return Arrays.asList("Octavia", "Rapid", "Fabia", "Kodiaq", "Yeti");
    }

    private List<String> createPorscheList() {
        return Arrays.asList("911 Carrera", "718 Boxster", "Cayenne", "Taycan", "Macan");
    }

    private List<String> createFordList() {
        return Arrays.asList("Kuga", "Focus", "Mustang", "Explorer", "Mondeo");
    }

    private Map<String, List<String>> makeAndModels() {
        Map<String, List<String>> makeAndModels = new HashMap<>();
        makeAndModels.put("BMW", createBMWList());
        makeAndModels.put("Toyota", createToyotaList());
        makeAndModels.put("Nissan", createNissanList());
        makeAndModels.put("Skoda", createSkodaList());
        makeAndModels.put("Porsche", createPorscheList());
        makeAndModels.put("Ford", createFordList());

        return makeAndModels;
    }

    public String getRandomMake() {
        List<String> makes = new ArrayList<>(makeAndModels.keySet());

        return makes.get(random.nextInt(makes.size()));
    }

    public String getRandomModel(String make) {
        List<String> models = makeAndModels.get(make);
        return models.get(random.nextInt(models.size()));
    }

    public int getRandomYear() {
        int yearMax = Calendar.getInstance().get(Calendar.YEAR);
        int yearMin = yearMax - yearDiff;

        return random.nextInt((yearMax - yearMin) + 1) + yearMin;
    }

    public int getRandomPrice() {
        return (int) Math.ceil((random.nextInt((carMaxPrice - carMinPrice) + 1) + carMinPrice)/10000.0) * 10000;
    }

    public int getRandomPower() {
        return random.nextInt((carMaxPower - carMinPower) + 1) + carMinPower;
    }

    public Car getRandomCar() {
        String carMake = getRandomMake();
        Car result = carService.createCar(carMake, getRandomModel(carMake), getRandomYear(), getRandomPower(), getRandomPrice());
        log.info("In getRandomCar - Created car: " + result.toString());
        return result;
    }
}
