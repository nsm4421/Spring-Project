package com.karma.board.domain.dto.request.post

import com.karma.board.domain.entity.post.PostEntity

data class CreatePostRequestDto(
    val title: String,
    val content: String,
    val createdBy: String,
)

fun CreatePostRequestDto.toEntity() = PostEntity(
    title = title,
    content = content,
    createdBy = createdBy
)
