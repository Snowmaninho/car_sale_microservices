package com.snowman.offer_generator.senders;

import com.snowman.common_libs.configs.RabbitConfig;
import com.snowman.common_libs.domain.Offer;
import com.snowman.offer_generator.generators.OfferGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Slf4j
@Service
public class OfferSender {

    private final Random random;
    private final int minOffers;
    private final int maxOffers;
    private int offerListSize;
    private ArrayList<Offer> offers;

    private static final int FIXED_DELAY = 10000; // create one offer every 10 sec
    private final RabbitTemplate template;
    private final OfferGenerator offerGenerator;

    {
        random = new Random();
        minOffers = 1;
        maxOffers = 4;
        offerListSize = random.nextInt((maxOffers - minOffers) + 1) + minOffers;
        offers = new ArrayList<>(offerListSize);
    }

    @Autowired
    public OfferSender(RabbitTemplate template, OfferGenerator offerGenerator) {
        this.template = template;
        this.offerGenerator = offerGenerator;
    }

    @Scheduled(fixedDelay = FIXED_DELAY, initialDelay = 5000)
    public void sendOffers() {
        if (offers.size() == offerListSize) {
            template.convertAndSend(RabbitConfig.OFFER_EXCHANGE, RabbitConfig.OFFER_ROUTING_KEY, offers);
            log.info("IN sendOffers: Created and sent " + offers.size() + " offers");
            offerListSize = random.nextInt((maxOffers - minOffers) + 1) + minOffers;
            offers.clear();
        } else {
            offers.add(offerGenerator.getRandomOffer());
        }
    }
}
