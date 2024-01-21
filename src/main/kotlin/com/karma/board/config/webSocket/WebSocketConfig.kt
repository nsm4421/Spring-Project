package com.karma.board.config.webSocket

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {
    companion object {
        private const val ENDPOINT = "/ws"
        private const val SIMPLE_BROKER = "/topic"
        private const val PUBLISH = "/app"
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker(SIMPLE_BROKER)
        registry.setApplicationDestinationPrefixes(PUBLISH)
    }

    // client가 웹소켓으로 접근할 end-point 설정 (localhost:8080/ws)
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint(ENDPOINT)
            .setAllowedOriginPatterns("*")
    }
}
