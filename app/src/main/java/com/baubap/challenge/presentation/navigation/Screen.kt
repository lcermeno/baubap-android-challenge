package com.baubap.challenge.presentation.navigation

import androidx.navigation3.runtime.NavKey
import com.baubap.challenge.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data object LoginKey : NavKey
@Serializable
data class HomeKey(val user: User) : NavKey
@Serializable
data object RegisterKey : NavKey