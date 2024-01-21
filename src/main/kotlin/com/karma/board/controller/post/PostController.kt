package com.karma.board.controller.post

import com.karma.board.domain.dto.request.post.CreatePostRequestDto
import com.karma.board.domain.dto.request.post.ModifyPostRequestDto
import com.karma.board.domain.dto.request.post.SearchPostRequestDto
import com.karma.board.domain.dto.response.post.PostResponseDto
import com.karma.board.service.post.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post")
class PostController(
    private val postService: PostService,
) {
    // TODO : 인증기능 구현 후, 로그인한 유저의 id로 접근하도록 수정
    val username = "karma"

    @GetMapping
    fun searchPost(
        pageable: Pageable,
        req: SearchPostRequestDto,
    ): Page<PostResponseDto> {
        return postService.searchPost(pageable, req)
    }

    @GetMapping("/{postId}")
    fun getPostById(@PathVariable postId: Long): PostResponseDto {
        return postService.getPostById(postId)
    }

    @PostMapping
    fun createPost(
        @RequestBody req: CreatePostRequestDto,
    ): Long {
        return postService.createPost(req = req, createdBy = username)
    }

    @PutMapping("/{postId}")
    fun modifyPost(
        @RequestBody req: ModifyPostRequestDto,
        @PathVariable postId: Long,
    ): Long {
        return postService.modifyPost(postId = postId, req = req, modifiedBy = username)
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
    ): Long {
        return postService.deletePost(postId = postId, deletedBy = username)
    }
}
