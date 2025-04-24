package vku.ddd.social_network_be.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ReactToPostRequest(
    @field:NotNull
    var postId: Long,

    @field:NotNull
    var userId: Long,

    @field:NotBlank
    var reaction: String = ""
)
