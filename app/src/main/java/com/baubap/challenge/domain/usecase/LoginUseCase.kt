package com.baubap.challenge.domain.usecase

import com.baubap.challenge.domain.model.Credentials
import com.baubap.challenge.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository

) {

    operator fun invoke(credentials: Credentials) =
        authRepository.login(credentials)
}