package com.snowman.common_libs.services;

import com.snowman.common_libs.domain.Car;
import com.snowman.common_libs.domain.Offer;

public interface OfferService {
    Offer createOffer(String title, String anonsName, String authorName, String carMark, String carModel,
                      int carYear, int carPower, int carPrice, String offerText);

    Offer createOffer(String title, String anonsName, String authorName, Car car, String offerText);

    Iterable<Offer> findAll();
    void saveOffer(Offer offer);
}
