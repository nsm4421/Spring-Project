package com.karma.board.service.post

import com.karma.board.domain.dto.request.post.CreatePostRequestDto
import com.karma.board.domain.dto.request.post.ModifyPostRequestDto
import com.karma.board.domain.dto.request.post.SearchPostRequestDto
import com.karma.board.domain.dto.request.post.toEntity
import com.karma.board.domain.dto.response.post.PostResponseDto
import com.karma.board.domain.dto.response.post.toResponse
import com.karma.board.domain.entity.post.PostEntity
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
class PostService(
    private val postRepository: PostRepository,
) {

    @Transactional(readOnly = true)
    fun getPostById(
        postId: Long,
    ): PostResponseDto {
        return findPostByIdOrElseThrow(postId).toResponse()
    }

    @Transactional(readOnly = true)
    fun searchPost(
        pageable: Pageable,
        req: SearchPostRequestDto,
    ): Page<PostResponseDto> {
        return postRepository.searchPost(pageable, req).toResponse()
    }

    fun createPost(
        req: CreatePostRequestDto,
        createdBy: String,
    ): Long {
        return postRepository.save(req.toEntity(createdBy)).id
    }

    fun modifyPost(
        postId: Long,
        req: ModifyPostRequestDto,
        modifiedBy: String,
    ): Long {
        val post = findPostByIdOrElseThrow(postId)
        if (post.createdBy != modifiedBy) {
            throw ValidationException(
                "createdBy:${post.createdBy},modifiedBy:$modifiedBy"
            )
        }
        post.update(req = req, modifiedBy = modifiedBy)
        return postRepository.save(post).id
    }

    fun deletePost(
        postId: Long,
        deletedBy: String,
    ): Long {
        val post = findPostByIdOrElseThrow(postId)
        if (post.createdBy != deletedBy) throw ValidationException("createdBy:${post.createdBy},deletedBy:$deletedBy")
        postRepository.deleteById(post.id)
        return post.id
    }

    private fun findPostByIdOrElseThrow(postId: Long): PostEntity {
        return postRepository.findByIdOrNull(postId) ?: throw EntityNotFoundException("post id $postId is not found")
    }
}
