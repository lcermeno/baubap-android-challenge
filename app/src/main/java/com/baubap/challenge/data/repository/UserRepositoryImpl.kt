package com.baubap.challenge.data.repository

import com.baubap.challenge.data.api.ApiService
import com.baubap.challenge.data.api.dto.ErrorResponse
import com.baubap.challenge.data.mapper.toDomain
import com.baubap.challenge.data.mapper.toDto
import com.baubap.challenge.domain.model.User
import com.baubap.challenge.domain.model.UserForm
import com.baubap.challenge.domain.repository.UserRepository
import com.baubap.challenge.domain.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson
) : UserRepository {

    override fun register(userForm: UserForm): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = apiService.register(userForm.toDto())

                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    if (registerResponse != null) {
                        val user = registerResponse.toDomain(email = userForm.email)
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
        val errorBody = response.errorBody()?.string()

        return try {
            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
            "Error de registro: ${errorResponse.error}"
        } catch (e: Exception) {
            when (response.code()) {
                400 -> "Error de registro: Email ya registrado o datos inválidos."
                401 -> "Error de registro: Credenciales incorrectas."
                403 -> "Error de registro: Acceso denegado. Verifica tu API key."
                404 -> "Error de registro: Servicio no encontrado."
                500 -> "Error del servidor. Intenta más tarde."
                else -> "Error de registro desconocido. Código: ${response.code()}"
            }
        }
    }
}