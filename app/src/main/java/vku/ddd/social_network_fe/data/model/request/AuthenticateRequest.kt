package vku.ddd.social_network_be.dto.request

import jakarta.validation.constraints.NotBlank

data class AuthenticateRequest (
    @NotBlank(message = "Username cannot be blank")
    val username: String,
    @NotBlank(message = "Password cannot be blank")
    val password: String,
)