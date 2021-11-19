package com.snowman.offer_generator.generators;

import com.snowman.common_libs.domain.Car;
import com.snowman.common_libs.domain.Offer;
import com.snowman.common_libs.services.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// generate Offer
@Component
@Slf4j
public class OfferGenerator {

    private final CarGenerator carGenerator;
    private final AuthorGenerator authorGenerator;
    private final OfferService offerService;

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
        return "I'm selling " +  car.getCarMake() + " " + car.getCarModel() +
                " " + car.getCarYear() + " for the lowest price in City, only "
                + car.getCarPrice() + ". Hurry up! Profitable proposition!";
    }

    public Offer getRandomOffer() {
        Car car = carGenerator.getRandomCar();
        Offer result = offerService.createOffer(getRandomTitle(car), getRandomAnons(car),
                getRandomAuthor(), car, getRandomOfferText(car));
        log.info("IN getRandomOffer - Created offer: Author - " + result.getAuthorName() + ", Anons name - " + result.getAnonsName()
                + ", Car make - " + result.getCar().getCarMake() + ", Car model - " + result.getCar().getCarModel()
                + ", Car Year - " + result.getCar().getCarYear() + ", Car Power - " + result.getCar().getCarPower()
                + ", Car Price - " + result.getCar().getCarPrice());
        return result;
    }
}
