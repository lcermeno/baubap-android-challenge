package com.baubap.challenge.data.api.dto

data class RegisterRequest(
    val email: String,
    val password: String
)

data class RegisterResponse(
    val id: Int,
    val token: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String
)

data class ErrorResponse(
    val error: String
)