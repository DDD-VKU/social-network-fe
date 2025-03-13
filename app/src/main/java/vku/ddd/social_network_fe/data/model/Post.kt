package vku.ddd.social_network_fe.data.model

import java.time.LocalDateTime

data class Post(
    val id: Long,
    var caption: String,
    var imageUrl: String,
    var likeCount: Long,
    var dislikeCount: Long,
    val createdAt: LocalDateTime,
    val childrenPosts: List<Post>,
    val refPost: Post,
    val comments: List<Comment>
)
