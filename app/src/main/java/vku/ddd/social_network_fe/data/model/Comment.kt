package vku.ddd.social_network_fe.data.model

import java.time.LocalDateTime

data class Comment (
    val id: Long,
    val content: String,
    val userId: Long,
    val username: String,
    val childrenComments: MutableList<Comment>,
    val parentCommentId: Long,
    val commentLevel: Long,
    val createdAt: String,
    val likeCount: Long,
    val dislikeCount: Long
)