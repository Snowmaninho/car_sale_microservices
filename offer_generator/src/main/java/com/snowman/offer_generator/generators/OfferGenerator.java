package com.snowman.offer_generator.generators;

import com.snowman.common_libs.domain.Car;
import com.snowman.common_libs.domain.Offer;
import com.snowman.common_libs.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferGenerator {

    private CarGenerator carGenerator;
    private AuthorGenerator authorGenerator;
    private OfferService offerService;

    @Autowired
    public OfferGenerator(CarGenerator carGenerator, AuthorGenerator authorGenerator, OfferService offerService) {
        this.carGenerator = carGenerator;
        this.authorGenerator = authorGenerator;
        this.offerService = offerService;
    }

    public String getRandomTitle(Car car) {
        return car.getCarMake() + " " + car.getCarModel() + " " + car.getCarYear();
    }

    public String getRandomAnons(Car car) {
        return "Selling " + car.getCarMake() + " " + car.getCarModel() + " " + car.getCarYear() + " year";
    }

    public String getRandomAuthor() {
        return authorGenerator.getRandomAuthor();
    }

    public String getRandomOfferText(Car car) {
        return "I'm selling " +  car.getCarMake() + " " + car.getCarModel() + " " + car.getCarYear() + " for the lowest price in City, only "
                + car.getCarPrice() + ". Hurry up! Profitable proposition!";
    }

    public Offer getRandomOffer() {
        Car car = carGenerator.getRandomCar();
        return offerService.createOffer(getRandomTitle(car), getRandomAnons(car),getRandomAuthor(), car, getRandomOfferText(car));
    }
}
