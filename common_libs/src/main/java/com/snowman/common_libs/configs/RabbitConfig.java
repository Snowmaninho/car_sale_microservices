package com.snowman.common_libs.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class RabbitConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    public static final String OFFER_QUEUE = "offerQueue";
    public static final String OFFER_EXCHANGE = "offerExchange";
    public static final String OFFER_ROUTING_KEY = "offer";

    public static final String REQUEST_QUEUE = "requestQueue";
    public static final String REQUEST_EXCHANGE = "requestExchange";
    public static final String REQUEST_ROUTING_KEY = "request";

    public static final String CREATE_TOKEN_QUEUE = "tokenQueue";
    public static final String TOKEN_EXCHANGE = "tokenExchange";
    public static final String CREATE_TOKEN_ROUTING_KEY = "token";

    public static final String VALIDATE_TOKEN_QUEUE = "validateTokenQueue";
    public static final String VALIDATE_TOKEN_ROUTING_KEY = "validateToken";


    @Bean
    public Queue offerQueue() {
        return new Queue(OFFER_QUEUE);
    }

    @Bean Queue requestQueue() {
        return new Queue(REQUEST_QUEUE);
    }

    @Bean Queue createTokenQueue() {
        return new Queue(CREATE_TOKEN_QUEUE);
    }

    @Bean Queue validateTokenQueue() {
        return new Queue(VALIDATE_TOKEN_QUEUE);
    }


    @Bean
    public DirectExchange offerDirectExchange() {
        return new DirectExchange(OFFER_EXCHANGE);
    }

    @Bean
    public DirectExchange requestDirectExchange() {
        return new DirectExchange(REQUEST_EXCHANGE);
    }

    @Bean
    public DirectExchange tokenDirectExchange() {
        return new DirectExchange(TOKEN_EXCHANGE);
    }

    @Bean
    public Binding offerBinding() {
        return BindingBuilder.bind(offerQueue()).to(offerDirectExchange()).with(OFFER_ROUTING_KEY);
    }

    @Bean
    public Binding requestBinding() {
        return BindingBuilder.bind(requestQueue()).to(requestDirectExchange()).with(REQUEST_ROUTING_KEY);
    }

    @Bean
    public Binding createTokenBinding() {
        return BindingBuilder.bind(createTokenQueue()).to(tokenDirectExchange()).with(CREATE_TOKEN_ROUTING_KEY);
    }

    @Bean
    public Binding validateTokenBinding() {
        return BindingBuilder.bind(validateTokenQueue()).to(tokenDirectExchange()).with(VALIDATE_TOKEN_ROUTING_KEY);
    }
}
