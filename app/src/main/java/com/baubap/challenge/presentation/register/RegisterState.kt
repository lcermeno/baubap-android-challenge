package com.baubap.challenge.presentation.register

data class RegisterState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordError: String? = null
)