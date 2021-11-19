package com.snowman.main_auto_service.listeners;

import com.snowman.common_libs.configs.RabbitConfig;
import com.snowman.common_libs.domain.Offer;
import com.snowman.common_libs.services.OfferService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableRabbit
public class OfferListener {

    private final OfferService offerService;

    @Autowired
    public OfferListener(OfferService offerService) {
        this.offerService = offerService;
    }

    // accept offers from other microservice and save it
    @RabbitListener(queues = RabbitConfig.OFFER_QUEUE)
    public void offerListener(List<Offer> offers) {
        offers.forEach(offerService::saveOffer);
    }
}
