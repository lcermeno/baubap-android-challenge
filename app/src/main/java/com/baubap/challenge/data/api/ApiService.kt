package com.baubap.challenge.data.api

import com.baubap.challenge.data.api.dto.LoginRequest
import com.baubap.challenge.data.api.dto.LoginResponse
import com.baubap.challenge.data.api.dto.RegisterRequest
import com.baubap.challenge.data.api.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

}