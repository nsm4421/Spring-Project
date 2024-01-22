package com.karma.board.domain.entity.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.karma.board.config.constant.UserRole
import com.karma.board.domain.dto.request.user.ModifyUserRequestDto
import jakarta.persistence.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime

@Entity
class UserEntity(
    @Column(unique = true) val username: String,
    rawPassword: String,
    email: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = ""

    @Column(unique = true)
    var email: String = email
        protected set

    @JsonIgnore
    var encodedPassword: String = BCryptPasswordEncoder().encode(rawPassword)
        protected set

    val createdAt: LocalDateTime = LocalDateTime.now()
    var modifiedAt: LocalDateTime? = null
        protected set

    @Enumerated
    val role: UserRole = UserRole.USER

    fun comparePassword(rawPassword: String) =
        BCryptPasswordEncoder().matches(rawPassword, this.encodedPassword)

    fun update(req: ModifyUserRequestDto) {
        this.email = req.email
        this.encodedPassword = BCryptPasswordEncoder().encode(req.rawPassword)
        this.modifiedAt = LocalDateTime.now()
    }
}
