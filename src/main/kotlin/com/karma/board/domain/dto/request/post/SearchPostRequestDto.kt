package com.karma.board.domain.dto.request.post

import org.springframework.web.bind.annotation.RequestParam

data class SearchPostRequestDto(
    @RequestParam
    val title: String?,
    @RequestParam
    val createdBy: String?,

)
