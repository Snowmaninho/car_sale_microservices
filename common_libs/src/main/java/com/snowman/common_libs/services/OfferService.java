package com.snowman.common_libs.services;

import com.snowman.common_libs.domain.Car;
import com.snowman.common_libs.domain.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface OfferService {
    Offer createOffer(String title, String anonsName, String authorName, String carMark, String carModel,
                      int carYear, int carPower, int carPrice, String offerText);

    Offer createOffer(String title, String anonsName, String authorName, Car car, String offerText);

//    Iterable<Offer> findAll();
//    Iterable<Offer> findAll(Pageable pageable);
    Page<Offer> findAll(Pageable pageable);

    Page<Offer> findFilteredOffers(String carMake, String carModel,
                                   Integer carMinYear, Integer carMaxYear,
                                   Integer carMinPrice, Integer carMaxPrice,
                                   Integer carMinHp, Integer carMaxHpm, Pageable pageable);

    void saveOffer(Offer offer);
}
