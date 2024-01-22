package com.karma.board.domain.dto.response.user

import com.karma.board.config.constant.UserRole
import com.karma.board.domain.entity.user.UserEntity
import io.swagger.v3.oas.annotations.media.Schema

data class SignInResponseDto(
    @Schema(description = "user id")
    val id: String?,
    @Schema(description = "username", example = "karma")
    val username: String?,
    @Schema(description = "role type", example = "USER")
    val role: UserRole,
    val token: String,
)

fun UserEntity.toResponse(token: String) = SignInResponseDto(
    id = id,
    username = username,
    role = role,
    token = token
)
