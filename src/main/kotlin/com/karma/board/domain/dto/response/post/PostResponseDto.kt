package com.karma.board.domain.dto.response.post

data class PostResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: String,
    val modifiedBy: String?,
    val modifiedAt: String?,
)
