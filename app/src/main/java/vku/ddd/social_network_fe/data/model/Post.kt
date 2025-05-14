package vku.ddd.social_network_fe.data.model

import java.time.LocalDateTime

data class Post(
    val id: Long,
    var caption: String,
    var username: String,
    var userFname: String,
    var userLname: String,
    var userId: Long,
    var avatarId: Long,
    var imageId: Long,
    var likesCount: Long,
    var dislikeCount: Long,
    var commentsCount: Long,
    var shareCount: Long,
    val createdAt: String,
    val childrenPosts: List<Post>,
    val refPost: Post
)
