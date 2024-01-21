package com.karma.board.domain.entity.post

import com.karma.board.domain.dto.request.post.ModifyPostRequestDto
import com.karma.board.domain.entity.base.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.ValidationException

@Entity
class PostEntity(
    title: String,
    content: String,
    createdBy: String,
) : BaseEntity(createdBy) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var title: String = title
        protected set
    var content: String = content
        protected set

    fun update(req: ModifyPostRequestDto) {
        if (req.modifiedBy != this.createdBy) {
            throw ValidationException("createdBy:$createdBy,modifiedBy:$modifiedBy")
        }
        this.title = req.title
        this.content = req.content
        super.update(req.modifiedBy) // update modifiedAt, modifiedBy
    }
}
