package com.karma.board.service

import com.karma.board.domain.dto.request.post.CreatePostRequestDto
import com.karma.board.domain.dto.request.post.ModifyPostRequestDto
import com.karma.board.domain.dto.request.post.SearchPostRequestDto
import com.karma.board.domain.entity.post.PostEntity
import com.karma.board.repository.PostRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import jakarta.validation.ValidationException
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostServiceTest(
    private val postService: PostService,
    private val postRepository: PostRepository,
) : BehaviorSpec({

    // 테스트 시작 전, 100개의 데이터를 DB에 삽입
    beforeSpec {
        val posts = (1..100).map {
            PostEntity(
                title = "title$it",
                content = "content$it",
                createdBy = "user$it"
            )
        }
        postRepository.saveAll(
            posts
        )
    }

    given("게시글 작성 성공") {
        When("정상 케이스") {
            val title = "create post test"
            val content = "create post test"
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
            var title = "modify post test"
            var content = "modify post test"
            val createdBy = "karma"
            var postId = postService.createPost(
                CreatePostRequestDto(
                    title = title,
                    content = content,
                    createdBy = createdBy
                )
            )
            title = "modified post"
            content = "modified post"
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

    given("게시글 페이지 불러오기 성공") {
        When("검색어가 없는 경우") {

            val pageNumber = 0
            val pageSize = 5

            val pages = postService.searchPost(
                PageRequest.of(pageNumber, pageSize),
                searchPostRequestDto = SearchPostRequestDto(
                    title = null,
                    createdBy = null
                )
            )

            then("게시글 페이지 정상반환") {
                pages.number shouldBe pageNumber
                pages.size shouldBe pageSize
                pages.content.size shouldBe pageSize
                pages.content[0].title shouldContain "test"
            }
        }
    }

    given("게시글 제목 검색 정상 동작") {
        When("제목이 주어진 경우") {

            val pageNumber = 0
            val pageSize = 5
            val searchKeyword = "title"

            val pages = postService.searchPost(
                PageRequest.of(pageNumber, pageSize),
                searchPostRequestDto = SearchPostRequestDto(
                    title = searchKeyword,
                    createdBy = null
                )
            )

            then("게시글 제목이 검색어를 포함함") {
                pages.number shouldBe pageNumber
                pages.size shouldBe pageSize
                pages.content.size shouldBe pageSize
                pages.content[0].title shouldContain searchKeyword
            }
        }
    }

    given("게시글 작성자 검색 정상 동작") {
        When("작성자 주어진 경우") {

            val pageNumber = 0
            val pageSize = 5
            val searchKeyword = "user1"

            val pages = postService.searchPost(
                PageRequest.of(pageNumber, pageSize),
                searchPostRequestDto = SearchPostRequestDto(
                    title = null,
                    createdBy = searchKeyword
                )
            )

            then("게시글 작성자가 검색어와 일치") {
                pages.content[0].createdBy shouldBe searchKeyword
            }
        }
    }
})
