package vku.ddd.social_network_be.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Null
import java.time.LocalDateTime

data class PostUpdateRequest(
    @field:NotNull
    var postId: Int,

    var caption: List<String>? = List(0) { "" },

    @field:Null
    var userId: Long? = null,

    @field:NotBlank
    var privacy: String = "",

    var createdAt: LocalDateTime? = null
)
