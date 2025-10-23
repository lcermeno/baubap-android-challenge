package com.baubap.challenge.data.repository

import android.util.Log
import com.baubap.challenge.data.api.ApiService
import com.baubap.challenge.data.api.dto.ErrorResponse
import com.baubap.challenge.data.mapper.toDto
import com.baubap.challenge.domain.model.Credentials
import com.baubap.challenge.domain.model.User
import com.baubap.challenge.domain.repository.AuthRepository
import com.baubap.challenge.domain.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val gson: Gson
) : AuthRepository {

    override fun login(credentials: Credentials): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = api.login(request = credentials.toDto())
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        val user = User(token = loginResponse.token, email = credentials.email)
                        emit(Resource.Success(user))
                    } else {
                        emit(Resource.Error("Respuesta vacía del servidor"))
                    }
                } else {
                    val errorMessage = parseError(response)
                    emit(Resource.Error(errorMessage))
                }
            } catch (e: IOException) {
                emit(Resource.Error("Error de red. Revisa tu conexión."))
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Ocurrió un error inesperado"))
            }
        }
    }

    private fun parseError(response: Response<*>): String {
        try {
            val errorBodyString = response.errorBody()?.string()
            if (!errorBodyString.isNullOrEmpty()) {
                val errorResponse = gson.fromJson(errorBodyString, ErrorResponse::class.java)
                if (errorResponse.error.isNotEmpty()) {
                    return errorResponse.error
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing error response: ${e.message}")
        }

        return when (response.code()) {
            400 -> "Error de login: Datos inválidos. Verifica email y contraseña."
            401 -> "Error de login: Credenciales incorrectas."
            403 -> "Error de login: Acceso denegado. Verifica tu API key."
            404 -> "Error de login: Servicio no encontrado."
            500 -> "Error del servidor. Intenta más tarde."
            else -> "Error de login desconocido. Código: ${response.code()}"
        }
    }

    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }
}