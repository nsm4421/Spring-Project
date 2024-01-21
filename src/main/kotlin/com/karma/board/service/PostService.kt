package com.karma.board.service

import com.karma.board.domain.dto.request.post.CreatePostRequestDto
import com.karma.board.domain.dto.request.post.ModifyPostRequestDto
import com.karma.board.domain.dto.request.post.toEntity
import com.karma.board.domain.dto.response.post.PostResponseDto
import com.karma.board.domain.entity.post.PostEntity
import com.karma.board.repository.PostRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
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
        id: Long,
    ): PostResponseDto {
        return findPostByIdOrElseThrow(id).toResponse()
    }

    // TODO : 검색기능구현하기
    @Transactional(readOnly = true)
    fun searchPost(
        id: Long,
    ): Page<PostResponseDto> {
        return Page.empty()
    }

    fun createPost(
        createPostRequestDto: CreatePostRequestDto,
    ): Long {
        return postRepository.save(createPostRequestDto.toEntity()).id
    }

    fun modifyPost(
        id: Long,
        modifyPostRequestDto: ModifyPostRequestDto,
    ): Long {
        val post = findPostByIdOrElseThrow(id)
        post.update(modifyPostRequestDto)
        return postRepository.save(post).id
    }

    fun deletePost(
        id: Long,
        deletedBy: String,
    ): Long {
        val post = findPostByIdOrElseThrow(id)
        if (post.createdBy == deletedBy) throw RuntimeException("createdBy:${post.createdBy},deletedBy:$deletedBy")
        postRepository.deleteById(post.id)
        return post.id
    }

    private fun findPostByIdOrElseThrow(id: Long): PostEntity {
        return postRepository.findByIdOrNull(id) ?: throw EntityNotFoundException("post id $id is not found")
    }
}
