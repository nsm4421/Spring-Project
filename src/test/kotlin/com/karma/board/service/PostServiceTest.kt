package com.karma.board.service

import com.karma.board.domain.dto.request.post.CreatePostRequestDto
import com.karma.board.domain.dto.request.post.ModifyPostRequestDto
import com.karma.board.repository.PostRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import jakarta.validation.ValidationException
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostServiceTest(
    private val postService: PostService,
    private val postRepository: PostRepository,
) : BehaviorSpec({

    given("게시글 작성 성공") {
        When("정상 케이스") {
            val title = "create post test title"
            val content = "create post test content"
            val createdBy = "karma"
            val postId = postService.createPost(
                CreatePostRequestDto(
                    title = title,
                    content = content,
                    createdBy = createdBy
                )
            )
            then("정상적으로 DB에 저장") {
                postId shouldBeGreaterThan 0L
                val post = postRepository.findByIdOrNull(postId)
                post shouldNotBe null
                post?.title shouldBe title
                post?.content shouldBe content
                post?.createdBy shouldBe createdBy
            }
        }
    }

    given("게시글 수정 성공") {
        When("정상 케이스") {
            var title = "modify post test title"
            var content = "modify post test content"
            val createdBy = "karma"
            var postId = postService.createPost(
                CreatePostRequestDto(
                    title = title,
                    content = content,
                    createdBy = createdBy
                )
            )
            title = "modified post content"
            content = "modified post content"
            postId = postService.modifyPost(
                id = postId,
                modifyPostRequestDto =
                ModifyPostRequestDto(
                    title = title,
                    content = content,
                    modifiedBy = createdBy
                )
            )
            then("포스트가 정상적으로 수정됨") {
                postId shouldBeGreaterThan 0L
                val post = postRepository.findByIdOrNull(postId)
                post shouldNotBe null
                post?.title shouldBe title
                post?.content shouldBe content
                post?.createdBy shouldBe createdBy
            }
        }
    }

    given("포스트 수정 실패") {
        When("작성자와 수정자가 서로 다르게 주어지는 경우") {
            var title = "modify post test title"
            var content = "modify post test content"
            val createdBy = "karma"
            val postId = postService.createPost(
                CreatePostRequestDto(
                    title = title,
                    content = content,
                    createdBy = createdBy
                )
            )
            title = "modified post content"
            content = "modified post content"

            then("EntityNotFoundException 오류 발생") {
                shouldThrow<ValidationException> {
                    postService.modifyPost(
                        id = postId,
                        modifyPostRequestDto =
                        ModifyPostRequestDto(
                            title = title,
                            content = content,
                            modifiedBy = "not $createdBy"
                        )
                    )
                }
            }
        }
    }
})
