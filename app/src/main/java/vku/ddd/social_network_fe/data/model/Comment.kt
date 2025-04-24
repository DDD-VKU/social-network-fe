package vku.ddd.social_network_fe.data.model

import java.time.LocalDateTime

data class Comment (
    val id: Long,
    var content: String,
    val userId: Long,
    val username: String,
    var childrenComments: MutableList<Comment>,
    var parentCommentId: Long,
    val commentLevel: Long,
    val createdAt: String,
    val likeCount: Long,
    val dislikeCount: Long,
    val avatarId: Long,
)