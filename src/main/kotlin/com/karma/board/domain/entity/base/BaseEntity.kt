package com.karma.board.domain.entity.base

import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity(
    val createdBy: String,
) {
    val createdAt: LocalDateTime = LocalDateTime.now()
    var modifiedAt: LocalDateTime? = null
        protected set
    var modifiedBy: String? = null
        protected set

    fun update(modifiedBy: String) {
        this.modifiedAt = LocalDateTime.now()
        this.modifiedBy = modifiedBy
    }
}
