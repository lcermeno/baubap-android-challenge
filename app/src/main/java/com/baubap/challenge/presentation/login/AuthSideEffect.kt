package com.baubap.challenge.presentation.login

import com.baubap.challenge.domain.model.User

sealed class AuthSideEffect {
    data class NavigateToHome(val user: User) : AuthSideEffect()
    data class ShowError(val message: String) : AuthSideEffect()
}