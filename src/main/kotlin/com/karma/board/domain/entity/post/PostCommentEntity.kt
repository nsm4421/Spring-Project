package com.karma.board.domain.entity.post

import com.karma.board.domain.dto.request.post.comment.ModifyPostCommentRequestDto
import com.karma.board.domain.entity.base.BaseEntity
import jakarta.persistence.*
import jakarta.validation.ValidationException

@Entity
class PostCommentEntity(
    post: PostEntity,
    content: String,
    createdBy: String,
) : BaseEntity(createdBy = createdBy) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var content: String = content
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    var post: PostEntity = post
        protected set

    fun update(req: ModifyPostCommentRequestDto, modifiedBy: String) {
        if (modifiedBy != this.createdBy) {
            throw ValidationException("createdBy:$createdBy,modifiedBy:$modifiedBy")
        }
        this.content = req.content
        super.update(modifiedBy) // update modifiedAt, modifiedBy
    }
}
