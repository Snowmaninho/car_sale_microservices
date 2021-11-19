package com.snowman.common_libs.repos;

import com.snowman.common_libs.domain.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface OfferRepo extends PagingAndSortingRepository<Offer, Long> {


    // big query for "ignore null or empty" and "ignore case"
    @Query("SELECT o FROM Offer o INNER JOIN o.car c WHERE (:carMake = '' or lower(c.carMake) = lower(:carMake)) and (:carModel = '' or lower(c.carModel) = lower(:carModel)) and (:carMinYear is null or c.carYear >= :carMinYear) " +
            "and (:carMaxYear is null or c.carYear <= :carMaxYear) and (:carMinPrice is null or c.carPrice >= :carMinPrice) and (:carMaxPrice is null or c.carPrice <= :carMaxPrice) " +
            "and (:carMinHp is null or c.carPower >= :carMinHp) and (:carMaxHp is null or c.carPower <= :carMaxHp)")
    Page<Offer> findAllByCarMakeAndCarModelAndCarYearAndCarPriceAndCarPower(@Param("carMake") String carMake, @Param("carModel") String carModel,
                                                                            @Param("carMinYear") Integer carMinYear, @Param("carMaxYear") Integer carMaxYear,
                                                                            @Param("carMinPrice") Integer carMinPrice, @Param("carMaxPrice") Integer carMaxPrice,
                                                                            @Param("carMinHp") Integer carMinHp, @Param("carMaxHp") Integer carMaxHp, Pageable pageable);

    Page<Offer> findByAuthorName(String author, Pageable pageable);
}



