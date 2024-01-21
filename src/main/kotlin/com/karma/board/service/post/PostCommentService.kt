package com.karma.board.service.post

import com.karma.board.domain.dto.request.post.comment.CreatePostCommentRequestDto
import com.karma.board.domain.dto.request.post.comment.ModifyPostCommentRequestDto
import com.karma.board.domain.dto.request.post.comment.toEntity
import com.karma.board.domain.dto.response.post.comment.PostCommentResponseDto
import com.karma.board.domain.dto.response.post.comment.toResponse
import com.karma.board.domain.entity.post.PostCommentEntity
import com.karma.board.domain.entity.post.PostEntity
import com.karma.board.repository.post.PostCommentRepository
import com.karma.board.repository.post.PostRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.validation.ValidationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostCommentService(
    private val postRepository: PostRepository,
    private val commentRepository: PostCommentRepository,
) {
    @Transactional(readOnly = true)
    fun getComments(
        pageable: Pageable,
        postId: Long,
    ): Page<PostCommentResponseDto> {
        return commentRepository.getComments(pageable, postId).toResponse()
    }

    fun createComment(
        postId: Long,
        req: CreatePostCommentRequestDto,
        createdBy: String,
    ): Long {
        val post = findPostByIdOrElseThrow(postId)
        val comment = req.toEntity(post, createdBy)
        return commentRepository.save(comment).id
    }

    fun modifyComment(
        commentId: Long,
        req: ModifyPostCommentRequestDto,
        modifiedBy: String,
    ): Long {
        val comment = findCommentByIdOrElseThrow(commentId)
        if (comment.createdBy != modifiedBy) {
            throw ValidationException(
                "createdBy:${comment.createdBy},modifiedBy:$modifiedBy"
            )
        }
        comment.update(req = req, modifiedBy = modifiedBy)
        return commentRepository.save(comment).id
    }

    fun deleteComment(
        commentId: Long,
        deletedBy: String,
    ): Long {
        val comment = findCommentByIdOrElseThrow(commentId)
        if (comment.createdBy != deletedBy) {
            throw ValidationException(
                "createdBy:${comment.createdBy},deletedBy:$deletedBy"
            )
        }
        commentRepository.deleteById(commentId)
        return commentId
    }

    private fun findPostByIdOrElseThrow(postId: Long): PostEntity {
        return postRepository.findByIdOrNull(postId) ?: throw EntityNotFoundException("post id $postId is not found")
    }

    private fun findCommentByIdOrElseThrow(commentId: Long): PostCommentEntity {
        return commentRepository.findByIdOrNull(commentId) ?: throw EntityNotFoundException(
            "comment id $commentId is not found"
        )
    }
}
