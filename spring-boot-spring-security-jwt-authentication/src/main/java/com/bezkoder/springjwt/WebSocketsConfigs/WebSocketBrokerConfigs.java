package com.bezkoder.springjwt.WebSocketsConfigs;
/**
 * Copyright © 2023 Mavenir Systems
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * @author Aditya Patil
 * @Date 07-03-2023
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfigs implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect").setAllowedOriginPatterns("*");
        registry.addEndpoint("/connect").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/subscribe/get-subscription");
        registry.setApplicationDestinationPrefixes("/app");
    }
}


