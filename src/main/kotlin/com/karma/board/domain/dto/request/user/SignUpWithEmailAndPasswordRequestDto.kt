package com.karma.board.domain.dto.request.user

import com.karma.board.domain.entity.user.UserEntity

data class SignUpWithEmailAndPasswordRequestDto(
    val username: String,
    val email: String,
    val rawPassword: String,
)

fun SignUpWithEmailAndPasswordRequestDto.toEntity() = UserEntity(
    username = username,
    email = email,
    rawPassword = rawPassword
)
