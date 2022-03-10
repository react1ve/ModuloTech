package com.example.domain.interactor

import com.example.domain.api.UserRepositoryApi
import com.example.domain.model.User

class UserInteractor(private val userRepositoryApi: UserRepositoryApi) {

    suspend fun getUser() = userRepositoryApi.getUser()

    suspend fun saveUser(user: User) = userRepositoryApi.saveUser(user)

    suspend fun initUsers() = userRepositoryApi.initUser()

    fun logOut() = userRepositoryApi.logOut()

    suspend fun setAppTheme(themeMode: Int) = userRepositoryApi.setAppTheme(themeMode)

    suspend fun getAppTheme() = userRepositoryApi.getAppTheme()

    suspend fun setAppLang(lang: String) = userRepositoryApi.setAppLang(lang)

}
