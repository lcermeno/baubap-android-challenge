package com.baubap.challenge.data.mapper

import com.baubap.challenge.data.api.dto.RegisterRequest
import com.baubap.challenge.data.api.dto.RegisterResponse
import com.baubap.challenge.domain.model.User
import com.baubap.challenge.domain.model.UserForm

fun UserForm.toDto() = RegisterRequest(
    email = email,
    password = password
)

fun RegisterResponse.toDomain(email: String) = User(
    id = id,
    token = token,
    email = email
)