package com.baubap.challenge.presentation.register

import androidx.lifecycle.ViewModel
import com.baubap.challenge.domain.model.UserForm
import com.baubap.challenge.domain.usecase.RegisterUseCase
import com.baubap.challenge.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel(), ContainerHost<RegisterState, RegisterSideEffect> {

    override val container = container<RegisterState, RegisterSideEffect>(
        initialState = RegisterState()
    )

    fun register() = intent {
        if (state.password != state.confirmPassword) {
            reduce { state.copy(passwordError = "Las contraseñas no coinciden") }
            return@intent
        }

        reduce { state.copy(passwordError = null) }

        val userFo = UserForm(
            email = state.email,
            password = state.password
        )

        registerUseCase(userFo)
            .onStart {
                reduce { state.copy(isLoading = true) }
            }
            .onEach { result ->
                when (val event = result) {
                    is Resource.Error -> {
                        reduce { state.copy(isLoading = false) }
                        postSideEffect(
                            RegisterSideEffect.ShowError(event.message ?: "Error desconocido")
                        )
                    }

                    is Resource.Loading -> {
                        reduce { state.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        val user = event.data
                        reduce { state.copy(isLoading = false) }

                        user?.let {
                            postSideEffect(RegisterSideEffect.NavigateToHome(it))
                        } ?: postSideEffect(RegisterSideEffect.ShowError("Error al registrar usuario"))
                    }
                }
            }
            .collect()
    }

    fun onEmailChanged(email: String) = intent {
        reduce { state.copy(email = email) }
    }

    fun onPasswordChanged(password: String) = intent {
        reduce { state.copy(password = password, passwordError = null) }
    }

    fun onConfirmPasswordChanged(confirmPassword: String) = intent {
        reduce { state.copy(confirmPassword = confirmPassword, passwordError = null) }
    }
}