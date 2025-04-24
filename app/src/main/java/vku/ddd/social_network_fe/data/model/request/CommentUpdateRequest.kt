package vku.ddd.social_network_be.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class CommentUpdateRequest(
    @field:NotNull
    var commentId: Long,

    @field:NotNull
    var userId: Long,

    @field:NotBlank
    var content: String = "",

    var updateAt: String = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
)
