package vku.ddd.social_network_fe.data.model

data class User(
    val id: Long,
    val fname: String,
    val lname: String,
    val username: String,
    val avatar: Long,
    val followersCount: Long,
    val followingCount: Long
)
