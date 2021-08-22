package com.snowman.common_libs.repos;

import com.snowman.common_libs.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepo extends JpaRepository<Offer, Long> {
}
