package vku.ddd.social_network_fe.data.model.response

import vku.ddd.social_network_fe.data.model.Account

data class AuthenticateResponse (
    var isSuccess: Boolean,
    var token: String,
    var account: Account
)