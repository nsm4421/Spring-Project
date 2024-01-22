package com.karma.board.domain.dto.request.user

data class SignInWithEmailAndPasswordRequestDto(
    val email: String,
    val rawPassword: String,
)
