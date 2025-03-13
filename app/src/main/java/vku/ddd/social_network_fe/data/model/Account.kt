package vku.ddd.social_network_fe.data.model

import java.util.Date

data class Account(
    val id: Long,
    val username: String,
    val email: String,
    val bio: String,
    val avatar: String,
    val dob: Date,
    val posts: List<Post>,
    val followers: List<Account>,
    val following: List<Account>
)
