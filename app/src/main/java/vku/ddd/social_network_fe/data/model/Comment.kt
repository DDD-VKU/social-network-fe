package vku.ddd.social_network_fe.data.model

import java.time.LocalDateTime

data class Comment (
    val id: Long,
    val content: String,
    val userId: Long,
    val username: String,
    val childComments: List<Comment>,
    val commentLevel: Long,
    val createdAt: LocalDateTime,
    val likeCount: Long,
    val dislikeCount: Long
)