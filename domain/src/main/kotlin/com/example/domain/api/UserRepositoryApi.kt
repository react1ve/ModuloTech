package com.example.domain.api

import com.example.domain.model.User

interface UserRepositoryApi {

    suspend fun getUser(): User?

    suspend fun saveUser(user: User)

    suspend fun initUser(): User?

}
