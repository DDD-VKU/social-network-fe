package vku.ddd.social_network_fe.data.model.response

import java.util.Date

data class AccountResponse(
    var id: Long,
    var username: String,
    var fname: String,
    var lname: String,
    var email: String,
    var bio: String = "",
    var avatar: Long,
    var dob: Date = Date(),
    var followers: Int = 0,
    var following: Int = 0
) 