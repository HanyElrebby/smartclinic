package com.smartinn.smartclinic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry config) {
        System.out.println("---------------------------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println("configureMessageBroker");
        System.out.println();
        System.out.println("---------------------------------------------------------------------");

        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        System.out.println("---------------------------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println("registerStompEndpoints");
        System.out.println();
        System.out.println("---------------------------------------------------------------------");

        registry.addEndpoint("/chat").setAllowedOrigins("*");
        registry.addEndpoint("/chat").setAllowedOrigins("*").withSockJS();
    }
}
