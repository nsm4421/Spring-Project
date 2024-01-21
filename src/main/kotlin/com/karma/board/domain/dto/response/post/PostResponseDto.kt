package com.karma.board.domain.dto.response.post

import com.karma.board.domain.entity.post.PostEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class PostResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: String,
    val modifiedBy: String?,
    val modifiedAt: String?,
)

/** Mapper */
fun PostEntity.toResponse() = PostResponseDto(
    id = id,
    title = title,
    content = content,
    createdBy = createdBy,
    createdAt = createdAt.toString(),
    modifiedBy = modifiedBy,
    modifiedAt = modifiedAt?.toString()
)

fun Page<PostEntity>.toResponse() = PageImpl(
    content.map { it.toResponse() },
    pageable,
    totalElements
)
