package com.example.data.repository

import com.example.data.datasource.database.DataSource
import com.example.domain.api.UserRepositoryApi
import com.example.domain.model.User

internal class UserRepository(private val dataSource: DataSource) : UserRepositoryApi {

    override suspend fun getUser(): User? = dataSource.getUser()

    override suspend fun saveUser(user: User) {
        dataSource.saveUser(user)
    }

    override suspend fun initUser(): User? = dataSource.initUser()
}
