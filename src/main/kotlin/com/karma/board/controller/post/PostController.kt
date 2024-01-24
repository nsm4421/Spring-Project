package com.karma.board.controller.post

import com.karma.board.domain.dto.request.post.CreatePostRequestDto
import com.karma.board.domain.dto.request.post.ModifyPostRequestDto
import com.karma.board.domain.dto.request.post.SearchPostRequestDto
import com.karma.board.domain.dto.response.post.PostResponseDto
import com.karma.board.service.post.PostService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@Tag(name = "포스팅 API")
@RestController
@RequestMapping("/post")
class PostController(
    private val postService: PostService,
) {

    //    TODO : 인증기능 구현하기
    private val username: String = "karma"

    @Operation(summary = "포스팅 페이지 조회")
    @GetMapping
    fun searchPost(
        pageable: Pageable,
        req: SearchPostRequestDto,
    ): Page<PostResponseDto> {
        return postService.searchPost(pageable, req)
    }

    @Operation(summary = "포스팅 단건 조회")
    @GetMapping("/{postId}")
    fun getPostById(@PathVariable postId: Long): PostResponseDto {
        return postService.getPostById(postId)
    }

    @Operation(summary = "포스팅 작성")
    @PostMapping
    fun createPost(
        @RequestBody req: CreatePostRequestDto,
    ): Long {
        return postService.createPost(req = req, createdBy = username)
    }

    @Operation(summary = "포스팅 수정")
    @PutMapping("/{postId}")
    fun modifyPost(
        @RequestBody req: ModifyPostRequestDto,
        @PathVariable postId: Long,
    ): Long {
        return postService.modifyPost(postId = postId, req = req, modifiedBy = username)
    }

    @Operation(summary = "포스팅 삭제")
    @DeleteMapping("/{postId}")
    fun deletePost(
        @PathVariable postId: Long,
    ): Long {
        return postService.deletePost(postId = postId, deletedBy = username)
    }
}
