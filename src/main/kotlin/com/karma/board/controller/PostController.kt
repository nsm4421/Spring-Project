package com.karma.board.controller

import com.karma.board.domain.dto.request.post.CreatePostRequestDto
import com.karma.board.domain.dto.request.post.ModifyPostRequestDto
import com.karma.board.domain.dto.request.post.SearchPostRequestDto
import com.karma.board.domain.dto.response.post.PostResponseDto
import com.karma.board.service.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

// TODO : 인증기능 구현 후, 로그인한 유저의 id로 접근하도록 수정
@RestController
@RequestMapping("/post")
class PostController(
    private val postService: PostService,
) {

    // TODO : 검색기능 구현하기
    @GetMapping
    fun searchPost(
        pageable: Pageable,
        searchPostRequestDto: SearchPostRequestDto,
    ): Page<PostResponseDto> {
        return Page.empty()
    }

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: Long): PostResponseDto {
        return postService.getPostById(id)
    }

    @PostMapping
    fun createPost(
        @RequestBody createPostRequestDto: CreatePostRequestDto,
    ): Long {
        return postService.createPost(createPostRequestDto = createPostRequestDto)
    }

    @PutMapping("/{id}")
    fun modifyPost(
        @RequestBody modifyPostRequestDto: ModifyPostRequestDto,
        @PathVariable id: Long,
    ): Long {
        return postService.modifyPost(id = id, modifyPostRequestDto = modifyPostRequestDto)
    }

    @DeleteMapping("/{id}")
    fun deletePost(
        @PathVariable id: Long,
    ): Long {
        return postService.deletePost(id = id, deletedBy = "karma")
    }
}
