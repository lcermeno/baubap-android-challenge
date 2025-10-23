package com.baubap.challenge.domain.repository

import com.baubap.challenge.domain.model.Credentials
import com.baubap.challenge.domain.model.User
import com.baubap.challenge.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun login(credentials: Credentials): Flow<Resource<User>>
}