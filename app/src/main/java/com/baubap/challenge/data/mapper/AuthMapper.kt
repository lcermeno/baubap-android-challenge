package com.baubap.challenge.data.mapper

import com.baubap.challenge.data.api.dto.LoginRequest
import com.baubap.challenge.domain.model.Credentials

fun Credentials.toDto() = LoginRequest(
    email = email,
    password = password
)