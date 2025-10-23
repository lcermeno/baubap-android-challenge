package com.baubap.challenge.presentation.login

import androidx.lifecycle.ViewModel
import com.baubap.challenge.domain.model.Credentials
import com.baubap.challenge.domain.usecase.LoginUseCase
import com.baubap.challenge.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel(), ContainerHost<AuthState, AuthSideEffect> {

    override val container = container<AuthState, AuthSideEffect>(
        initialState = AuthState()
    )

    fun login() = intent {

        val credentials = Credentials(
            email = state.email, password = state.password
        )

        loginUseCase(credentials).onStart {
            reduce { state.copy(isLoading = true) }
        }.onEach { result ->
            when (val event = result) {
                is Resource.Error -> {

                    reduce {
                        state.copy(isLoading = false)
                    }

                    postSideEffect(
                        AuthSideEffect.ShowError(event.message ?: "Error desconocido")
                    )
                }

                is Resource.Loading -> {
                    reduce { state.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    reduce {
                        state.copy(
                            isLoading = false, user = event.data
                        )
                    }
                    event.data?.let {
                        postSideEffect(AuthSideEffect.NavigateToHome(it))
                    } ?: postSideEffect(AuthSideEffect.ShowError("Error desconocido"))
                }
            }
        }.collect()
    }

    fun onEmailChanged(email: String) = intent {
        reduce {
            state.copy(email = email)
        }
    }

    fun onPasswordChanged(password: String) = intent {
        reduce {
            state.copy(password = password)
        }
    }
}