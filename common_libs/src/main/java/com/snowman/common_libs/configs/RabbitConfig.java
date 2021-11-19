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

    public static final String OFFER_EXCHANGE = "offerExchange";
    public static final String OFFER_QUEUE = "offerQueue";
    public static final String OFFER_ROUTING_KEY = "offerKey";

    public static final String AUTH_EXCHANGE = "authExchange";
    public static final String AUTH_QUEUE = "authQueue";
    public static final String AUTH_ROUTING_KEY = "authKey";

    public static final String TOKEN_EXCHANGE = "tokenExchange";

    public static final String CREATE_TOKEN_QUEUE = "createTokenQueue";
    public static final String CREATE_TOKEN_ROUTING_KEY = "createTokenKey";

    public static final String VALIDATE_TOKEN_QUEUE = "validateTokenQueue";
    public static final String VALIDATE_TOKEN_ROUTING_KEY = "validateTokenKey";

    public static final String RESOLVE_TOKEN_QUEUE = "resolveTokenQueue";
    public static final String RESOLVE_TOKEN_ROUTING_KEY = "resolveTokenKey";

    // Exchanges
    @Bean
    public DirectExchange offerDirectExchange() {
        return new DirectExchange(OFFER_EXCHANGE);
    }

    @Bean
    public DirectExchange authDirectExchange() {
        return new DirectExchange(AUTH_EXCHANGE);
    }

    @Bean
    public DirectExchange tokenDirectExchange() {
        return new DirectExchange(TOKEN_EXCHANGE);
    }

    // Queues
    @Bean
    public Queue offerQueue() {
        return new Queue(OFFER_QUEUE);
    }

    @Bean
    public Queue authQueue() {
        return new Queue(AUTH_QUEUE);
    }

    @Bean Queue createTokenQueue() {
        return new Queue(CREATE_TOKEN_QUEUE);
    }

    @Bean Queue validateTokenQueue() {
        return new Queue(VALIDATE_TOKEN_QUEUE);
    }

    @Bean Queue resolveTokenQueue() {
        return new Queue(RESOLVE_TOKEN_QUEUE);
    }

    // Bindings
    @Bean
    public Binding offerBinding() {
        return BindingBuilder.bind(offerQueue()).to(offerDirectExchange()).with(OFFER_ROUTING_KEY);
    }

    @Bean
    public Binding authBinding() {
        return BindingBuilder.bind(authQueue()).to(authDirectExchange()).with(AUTH_ROUTING_KEY);
    }

    @Bean
    public Binding createTokenBinding() {
        return BindingBuilder.bind(createTokenQueue()).to(tokenDirectExchange()).with(CREATE_TOKEN_ROUTING_KEY);
    }

    @Bean
    public Binding validateTokenBinding() {
        return BindingBuilder.bind(validateTokenQueue()).to(tokenDirectExchange()).with(VALIDATE_TOKEN_ROUTING_KEY);
    }

    @Bean
    public Binding resolveTokenBinding() {
        return BindingBuilder.bind(resolveTokenQueue()).to(tokenDirectExchange()).with(RESOLVE_TOKEN_ROUTING_KEY);
    }

    // EncoderBean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
