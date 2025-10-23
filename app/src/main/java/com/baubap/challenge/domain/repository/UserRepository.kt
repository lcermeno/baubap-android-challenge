package com.baubap.challenge.domain.repository

import com.baubap.challenge.domain.model.User
import com.baubap.challenge.domain.model.UserForm
import com.baubap.challenge.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun register(userForm: UserForm): Flow<Resource<User>>
}