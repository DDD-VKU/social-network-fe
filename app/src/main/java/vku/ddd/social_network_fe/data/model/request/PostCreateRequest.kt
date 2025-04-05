package vku.ddd.social_network_be.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class PostCreateRequest(
    var caption: MutableList<String> = MutableList(0) { "" },

    @field:NotNull
    var userId: Long,

    @field:NotBlank
    var privacy: String = "",

    var createdAt: LocalDateTime? = null
)
