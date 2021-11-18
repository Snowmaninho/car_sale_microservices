package com.snowman.common_libs.services.impl;

import com.snowman.common_libs.domain.AppUser;
import com.snowman.common_libs.domain.Car;
import com.snowman.common_libs.domain.Offer;
import com.snowman.common_libs.repos.OfferRepo;
import com.snowman.common_libs.services.CarService;
import com.snowman.common_libs.services.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OfferServiceImpl implements OfferService {

    private final OfferRepo offerRepo;
    private final CarService carService;

    @Autowired
    public OfferServiceImpl(OfferRepo offerRepo, CarService carService) {
        this.offerRepo = offerRepo;
        this.carService = carService;
    }

    @Override
    public Offer createOffer(String title, String anonsName, String authorName, String carMake, String carModel,
                             Integer carYear, Integer carPower, Integer carPrice, String offerText) {
        Car car = carService.createCar(carMake, carModel, carYear, carPower, carPrice);
        carService.saveCar(car);
        Offer result = new Offer().setTitle(title).setAnonsName(anonsName).setAuthorName(authorName)
                .setCar(car).setOfferText(offerText);
        log.info("IN createOffer - created offer with Author: {} and car: {}", authorName, car);
        return result;
    }

    @Override
    public Offer createOffer(String title, String anonsName, String authorName, Car car, String offerText) {
        carService.saveCar(car);
        Offer result = new Offer().setTitle(title).setAnonsName(anonsName)
                .setAuthorName(authorName).setCar(car).setOfferText(offerText);
        log.info("IN createOffer - created offer with Author: {} and car: {}", authorName, car);
        return result;
    }

    @Override
    public Page<Offer> findAllOffers(Pageable pageable) {
        Page<Offer> result = offerRepo.findAll(pageable);
        log.info("IN findAllOffers - {} offers found", result.getTotalElements());
        return result;
    }

    @Override
    public Page<Offer> findFilteredOffers(String carMake, String carModel, Integer carMinYear, Integer carMaxYear,
                                          Integer carMinPrice, Integer carMaxPrice, Integer carMinHp,
                                          Integer carMaxHp, Pageable pageable) {
        Page<Offer> result = offerRepo.findAllByCarMakeAndCarModelAndCarYearAndCarPriceAndCarPower(carMake, carModel,
                carMinYear, carMaxYear, carMinPrice, carMaxPrice, carMinHp, carMaxHp, pageable);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((carMake.equals("") ? "" : " make: " + carMake))
                .append(carModel.equals("") ? "" : " model: " + carModel)
                        .append(carMinYear == null ? "" : " min year: " + carMinYear)
                .append(carMaxYear == null ? "" : " max year: " + carMaxYear)
                .append(carMinPrice == null ? "" : " min price: " + carMinPrice)
                .append(carMaxPrice == null ? "" : " max price: " + carMaxPrice)
                .append(carMinHp == null ? "" : " min HP: " + carMinHp)
                .append(carMaxHp == null ? "" : " max HP: " + carMaxHp);

        log.info("IN findFilteredOffers - {} offers found with filter:" + stringBuilder, result.getTotalElements());
        return result;
    }

    @Override
    public void saveOffer(Offer offer) {
        offerRepo.save(offer);
        log.info("IN saveOffer - saved offer with Author: {} and car: {}", offer.getAuthorName(), offer.getCar());
    }

    @Override
    public Offer findOfferById(Long id) {

        Offer result = offerRepo.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findOfferById - no offer found by id: {}", id);
            return null;
        }

        log.info("IN findOfferById - offer with Author: {} and car: {} found by id: {}",
                result.getAuthorName(), result.getCar(), id);
        return result;

    }

    @Override
    public Page<Offer> findOffersByAuthorName(String author, Pageable pageable) {
        Page<Offer> result = offerRepo.findByAuthorName(author, pageable);
        log.info("IN findOfferByAuthorName - {} offers found by Author: {}", result.getNumberOfElements(), author);
        return result;
    }
}
