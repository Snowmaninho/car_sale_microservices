package com.snowman.common_libs.services;

import com.snowman.common_libs.domain.Offer;
import org.springframework.stereotype.Service;

@Service
public interface OfferService {
    Offer createOffer(String title, String anonsName, String authorName, String carMark, String carModel,
                      String carYear, String carPower, String carPrice, String offerText);
    Iterable<Offer> findAll();
    void saveOffer(Offer offer);
}
