package vku.ddd.social_network_be.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.*

data class AccountCreateRequest(
    @field:NotBlank
    @field:Size(min = 6, message = "User name must have at least 6 characters")
    var username: String? = null,

    @field:NotBlank
    var fname: String = "",

    @field:NotBlank
    var lname: String = "",

    @field:NotBlank
    var email: String = "",

    @field:NotBlank
    @field:Size(min = 8, max = 100, message = "Password must have at least 8 characters")
    var password: String = "",

    var passwordConfirm: String = "",

    var gender: Int = 0,

    @field:NotNull
    var dob: Date
)
