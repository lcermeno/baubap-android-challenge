package com.baubap.challenge.domain.usecase

import com.baubap.challenge.domain.model.UserForm
import com.baubap.challenge.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    operator fun invoke(userForm: UserForm) = userRepository.register(userForm)
}