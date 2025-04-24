package vku.ddd.social_network_fe.data.model.response

data class ApiResponse<T> (

    var code: Int,
    var message: String,
    var data: T
)