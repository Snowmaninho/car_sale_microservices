package com.snowman.common_libs.services;

import com.snowman.common_libs.domain.Car;
import com.snowman.common_libs.domain.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

public interface OfferService {
    Offer createOffer(String title, String anonsName, String authorName, String carMark, String carModel,
                      Integer carYear, Integer carPower, Integer carPrice, String offerText);

    Offer createOffer(String title, String anonsName, String authorName, Car car, String offerText);

    Page<Offer> findAllOffers(Pageable pageable);

    Page<Offer> findFilteredOffers(String carMake, String carModel,
                                   Integer carMinYear, Integer carMaxYear,
                                   Integer carMinPrice, Integer carMaxPrice,
                                   Integer carMinHp, Integer carMaxHpm, Pageable pageable);

    void saveOffer(Offer offer);

    Offer findOfferById(Long id);
    Page<Offer> findOffersByAuthorName(String author, Pageable pageable);
}
