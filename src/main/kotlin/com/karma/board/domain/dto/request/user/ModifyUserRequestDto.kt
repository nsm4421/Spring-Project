package com.karma.board.domain.dto.request.user

data class ModifyUserRequestDto(
    val email: String,
    val rawPassword: String,
)
