package com.modulo.domain.api

import com.modulo.domain.model.User

interface UserRepositoryApi {

    suspend fun getUser(): User?

    suspend fun saveUser(user: User)

    suspend fun initUser(): User?

    suspend fun setAppTheme(theme: Int)

    suspend fun getAppTheme(): Int

    suspend fun setAppLang(lang: String)

    fun logOut()
}
