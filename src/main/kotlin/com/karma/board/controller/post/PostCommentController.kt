package com.karma.board.controller.post

import com.karma.board.domain.dto.request.post.comment.CreatePostCommentRequestDto
import com.karma.board.domain.dto.request.post.comment.ModifyPostCommentRequestDto
import com.karma.board.domain.dto.response.post.comment.PostCommentResponseDto
import com.karma.board.service.post.PostCommentService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post-comment")
class PostCommentController(
    private val postCommentService: PostCommentService,
) {

    // TODO : 인증기능 구현 후, 로그인한 유저의 id로 접근하도록 수정
    val username = "karma"

    @GetMapping("/{postId}")
    fun getComments(
        pageable: Pageable,
        @PathVariable postId: Long,
    ): Page<PostCommentResponseDto> {
        return postCommentService.getComments(pageable = pageable, postId = postId)
    }

    @PostMapping("{postId}")
    fun createComment(
        @RequestBody req: CreatePostCommentRequestDto,
        @PathVariable postId: Long,
    ): Long {
        return postCommentService.createComment(postId = postId, req = req, createdBy = username)
    }

    @PutMapping("/{commentId}")
    fun modifyComment(
        @RequestBody req: ModifyPostCommentRequestDto,
        @PathVariable commentId: Long,
    ): Long {
        return postCommentService.modifyComment(commentId = commentId, req = req, modifiedBy = username)
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
    ): Long {
        return postCommentService.deleteComment(commentId = commentId, deletedBy = username)
    }
}
