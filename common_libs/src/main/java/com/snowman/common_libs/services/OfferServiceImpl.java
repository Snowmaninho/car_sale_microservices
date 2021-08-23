package com.snowman.common_libs.services;

import com.snowman.common_libs.domain.Car;
import com.snowman.common_libs.domain.Offer;
import com.snowman.common_libs.repos.OfferRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferRepo offerRepo;
    private CarService carService;

    @Autowired
    public OfferServiceImpl(OfferRepo offerRepo, CarService carService) {
        this.offerRepo = offerRepo;
        this.carService = carService;
    }

    @Override
    public Offer createOffer(String title, String anonsName, String authorName, String carMake, String carModel, int carYear, int carPower, int carPrice, String offerText) {
        Car car = carService.createCar(carMake, carModel, carYear, carPower, carPrice);
        carService.saveCar(car);
        return new Offer().setTitle(title).setAnonsName(anonsName).setAuthorName(authorName).setCar(car).setOfferText(offerText);
    }

    @Override
    public Offer createOffer(String title, String anonsName, String authorName, Car car, String offerText) {
        carService.saveCar(car);
        return new Offer().setTitle(title).setAnonsName(anonsName).setAuthorName(authorName).setCar(car).setOfferText(offerText);
    }

    @Override
    public Iterable<Offer> findAll() {
        return offerRepo.findAll();
    }

    @Override
    public void saveOffer(Offer offer) {
        offerRepo.save(offer);
    }
}
