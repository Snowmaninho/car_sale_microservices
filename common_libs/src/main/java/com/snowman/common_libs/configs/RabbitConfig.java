package com.snowman.common_libs.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String OFFER_QUEUE = "offerQueue";
    public static final String OFFER_EXCHANGE = "offerExchange";
    public static final String OFFER_ROUTING_KEY = "offer";

    @Bean
    public Queue offerQueue() {
        return new Queue(OFFER_QUEUE);
    }

    @Bean
    public DirectExchange offerDirectExchange() {
        return new DirectExchange(OFFER_EXCHANGE);
    }

    @Bean
    public Binding offerBinding() {
        return BindingBuilder.bind(offerQueue()).to(offerDirectExchange()).with(OFFER_ROUTING_KEY);
    }
}
