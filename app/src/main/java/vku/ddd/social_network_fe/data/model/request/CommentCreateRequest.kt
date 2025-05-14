package vku.ddd.social_network_be.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class CommentCreateRequest(
    @field:NotNull
    var userId: Long,

    @field:NotNull
    var postId: Long,

    var parentCommentId: Long? = null,

    @field:NotBlank
    var content: String = "",

    var commentLevel: Long = 0,

    var createdAt: String = LocalDateTime.now()
        .withNano((LocalDateTime.now().nano / 1_000_000) * 1_000_000) // Giới hạn đến mili giây
        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
)
