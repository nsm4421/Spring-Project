package com.karma.board.domain.dto.response.post.comment

import com.karma.board.domain.entity.post.PostCommentEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class PostCommentResponseDto(
    val id: Long,
    val content: String,
    val createdBy: String,
    val createdAt: String,
    val modifiedBy: String?,
    val modifiedAt: String?,
)

/** Mapper */
fun PostCommentEntity.toResponse() = PostCommentResponseDto(
    id = id,
    content = content,
    createdBy = createdBy,
    createdAt = createdAt.toString(),
    modifiedBy = modifiedBy,
    modifiedAt = modifiedAt?.toString()
)

fun Page<PostCommentEntity>.toResponse() = PageImpl(
    content.map { it.toResponse() },
    pageable,
    totalElements
)
