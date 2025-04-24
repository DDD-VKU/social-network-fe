package vku.ddd.social_network_be.dto.request

import jakarta.validation.constraints.NotNull

class ReactToCommentRequest (
    @field:NotNull
    var commentId : Long,

    @field:NotNull
    var reaction: String,

    @field:NotNull
    var userId : Long
)