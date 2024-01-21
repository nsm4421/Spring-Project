package com.karma.board.repository.post

import com.karma.board.domain.entity.post.PostCommentEntity
import com.karma.board.domain.entity.post.QPostCommentEntity.postCommentEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

interface BasePostCommentRepository {
    fun getComments(
        pageable: Pageable,
        postId: Long,
    ): Page<PostCommentEntity>
}

class BasePostCommentRepositoryImpl : BasePostCommentRepository,
    QuerydslRepositorySupport(PostCommentEntity::class.java) {
    override fun getComments(pageable: Pageable, postId: Long): Page<PostCommentEntity> {
        val fetched = from(postCommentEntity)
            .where(
                postId.let {
                    postCommentEntity.post.id.eq(it)
                }
            )
            .orderBy(postCommentEntity.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()
        return PageImpl(fetched.results, pageable, fetched.total)
    }
}

@Repository
interface PostCommentRepository : JpaRepository<PostCommentEntity, Long>, BasePostCommentRepository
