package com.bezkoder.springjwt.WebSocketsConfigs;
/**
 * Copyright © 2023 Mavenir Systems
 */

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author Aditya Patil
 * @Date 07-03-2023
 */
public class WebSocketBrokerConfigs implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect");
        registry.addEndpoint("/connect").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/return");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
