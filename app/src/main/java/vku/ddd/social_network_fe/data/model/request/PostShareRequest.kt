package vku.ddd.social_network_be.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class PostShareRequest(
    @field:NotNull
    var refPostId: Long,

    @field:NotBlank
    var caption: String = "",

    @field:NotNull
    var userId: Long,

    @field:NotBlank
    var privacy: String = "",

    var createdAt: String = LocalDateTime.now().toString()
)
