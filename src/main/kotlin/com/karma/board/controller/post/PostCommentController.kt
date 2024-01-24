package com.karma.board.controller.post

import com.karma.board.domain.dto.request.post.comment.CreatePostCommentRequestDto
import com.karma.board.domain.dto.request.post.comment.ModifyPostCommentRequestDto
import com.karma.board.domain.dto.response.post.comment.PostCommentResponseDto
import com.karma.board.service.post.PostCommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@Tag(name = "댓글 API")
@RestController
@RequestMapping("/post-comment")
class PostCommentController(
    private val postCommentService: PostCommentService,
) {

    private val username: String = "karma"

    @Operation(summary = "댓글 페이지 조회")
    @GetMapping("/{postId}")
    fun getComments(
        pageable: Pageable,
        @PathVariable postId: Long,
    ): Page<PostCommentResponseDto> {
        return postCommentService.getComments(pageable = pageable, postId = postId)
    }

    @Operation(summary = "댓글 작성")
    @PostMapping("{postId}")
    fun createComment(
        @RequestBody req: CreatePostCommentRequestDto,
        @PathVariable postId: Long,
    ): Long {
        return postCommentService.createComment(postId = postId, req = req, createdBy = username)
    }

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    fun modifyComment(
        @RequestBody req: ModifyPostCommentRequestDto,
        @PathVariable commentId: Long,
    ): Long {
        return postCommentService.modifyComment(commentId = commentId, req = req, modifiedBy = username)
    }

    @Operation(summary = "댓글 삭제")

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
    ): Long {
        return postCommentService.deleteComment(commentId = commentId, deletedBy = username)
    }
}
