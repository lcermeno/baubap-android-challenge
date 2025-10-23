package com.baubap.challenge.presentation.login

import com.baubap.challenge.domain.model.User

data class AuthState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val user: User? = null
)