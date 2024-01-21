package com.karma.board.domain.dto.request.post.comment

import com.karma.board.domain.entity.post.PostCommentEntity
import com.karma.board.domain.entity.post.PostEntity

class CreatePostCommentRequestDto(
    val content: String,
)

fun CreatePostCommentRequestDto.toEntity(
    post: PostEntity,
    createdBy: String,
) = PostCommentEntity(
    post = post,
    content = content,
    createdBy = createdBy
)
