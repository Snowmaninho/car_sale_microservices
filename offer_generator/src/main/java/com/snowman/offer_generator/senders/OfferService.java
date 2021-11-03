package com.snowman.offer_generator.senders;

import com.snowman.common_libs.configs.RabbitConfig;
import com.snowman.common_libs.domain.Offer;
import com.snowman.offer_generator.generators.OfferGenerator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@EnableScheduling
//@Component
@Service
public class OfferService {

    private Random random;
    private int minOffers;
    private int maxOffers;
    private int offerListSize;
    private ArrayList<Offer> offers;
    private static final int FIXED_DELAY = 3000;
    private RabbitTemplate template;
    private OfferGenerator offerGenerator;

    {
        random = new Random();
        minOffers = 2;
        maxOffers = 10;
        offerListSize = random.nextInt((maxOffers - minOffers) + 1) + minOffers;
        offers = new ArrayList<>(offerListSize);
    }

    @Autowired
    public OfferService(RabbitTemplate template, OfferGenerator offerGenerator) {
        this.template = template;
        this.offerGenerator = offerGenerator;
    }

    @Scheduled(fixedDelay = FIXED_DELAY)
    public void sendOffers() {
        if (offers.size() == offerListSize) {
            template.convertAndSend(RabbitConfig.OFFER_EXCHANGE, RabbitConfig.OFFER_ROUTING_KEY, offers);
            offerListSize = random.nextInt((maxOffers - minOffers) + 1) + minOffers;
            offers.clear();
        } else {
            offers.add(offerGenerator.getRandomOffer());
        }
    }
}
