package vku.ddd.social_network_fe.data.model

import java.util.Date

data class Account(
    var id: Long,
    var username: String,
    var fname: String,
    var lname: String,
    var email: String,
    var bio: String = "",
    var avatar: Long,
    var dob: Date = Date(),
    var posts: List<Post> = emptyList(),
    var followers: List<User> = emptyList(),
    var following: List<User> = emptyList()
)
