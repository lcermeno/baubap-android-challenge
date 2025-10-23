package com.baubap.challenge.presentation.register

import com.baubap.challenge.domain.model.User

sealed class RegisterSideEffect {
    data class NavigateToHome(val user: User) : RegisterSideEffect()
    data class ShowError(val message: String) : RegisterSideEffect()
}