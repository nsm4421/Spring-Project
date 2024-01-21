package com.karma.board.controller.chat

import com.karma.board.domain.dto.request.chat.ChatMessageRequestDto
import com.karma.board.domain.dto.response.chat.ChatMessageResponseDto
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.util.HtmlUtils

@Controller
class ChatController {
    private val logger = KotlinLogging.logger {}

    @MessageMapping("/chat-message") // client가 보낸 메세지를 처리할 주소
    @SendTo("/topic/chatting") // 메세지 처리가 끝난 뒤 메세지 브로커로 보낼 곳
    fun chatting(req: ChatMessageRequestDto): ChatMessageResponseDto {
        logger.debug { req.message }
        return ChatMessageResponseDto(
            HtmlUtils.htmlEscape(req.message)
        )
    }
}
