package com.karma.board.repository

import com.karma.board.domain.dto.request.post.SearchPostRequestDto
import com.karma.board.domain.entity.post.PostEntity
import com.karma.board.domain.entity.post.QPostEntity.postEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

interface BasePostRepository {
    fun searchPost(pageable: Pageable, searchPostRequestDto: SearchPostRequestDto): Page<PostEntity>
}

/** Custom Query Method On Post */
class BasePostRepositoryImpl : BasePostRepository, QuerydslRepositorySupport(PostEntity::class.java) {
    override fun searchPost(pageable: Pageable, searchPostRequestDto: SearchPostRequestDto): Page<PostEntity> {
        val fetched = from(postEntity).where(
            // 제목으로 조회
            searchPostRequestDto.title?.let {
                postEntity.title.contains(it)
            },
            // 작성자로 조회
            searchPostRequestDto.createdBy?.let {
                postEntity.createdBy.eq(it)
            }
        )
            .orderBy(postEntity.createdAt.desc()) // 작성시간으로 내림차순
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()
        return PageImpl(fetched.results, pageable, fetched.total)
    }
}

@Repository
interface PostRepository : JpaRepository<PostEntity, Long>, BasePostRepository
