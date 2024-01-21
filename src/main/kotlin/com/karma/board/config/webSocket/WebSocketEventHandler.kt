package com.karma.board.config.webSocket

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionSubscribeEvent

@Component
class WebSocketEventHandler {
    private val logger = KotlinLogging.logger {}

    @EventListener
    fun onConnect(event: SessionSubscribeEvent) {
        logger.debug { "onConnect" }
    }

    @EventListener
    fun onDisconnected(event: SessionSubscribeEvent) {
        logger.debug { "onDisconnected" }
    }

    @EventListener
    fun onSubscribe(event: SessionSubscribeEvent) {
        logger.debug { "onSubscribe" }
    }

    @EventListener
    fun onUnSubscribe(event: SessionSubscribeEvent) {
        logger.debug { "onUnSubscribe" }
    }
}
