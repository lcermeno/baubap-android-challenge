package com.baubap.challenge.domain.model

import kotlinx.serialization.Serializable

const val NO_USER_ID = -1

@Serializable
data class User(
    val id: Int = NO_USER_ID,
    val token: String,
    val email: String,
)