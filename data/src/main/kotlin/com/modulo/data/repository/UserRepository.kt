package com.modulo.data.repository

import com.modulo.data.database.mapper.UserModelMapper
import com.modulo.data.network.ApiDataSource
import com.modulo.data.preferences.SharedPreferencesManager
import com.modulo.domain.api.UserRepositoryApi
import com.modulo.domain.model.User

internal class UserRepository(
    private val preferencesManager: SharedPreferencesManager,
    private val apiDataSource: ApiDataSource,
) : UserRepositoryApi {

    override suspend fun getUser(): User? = preferencesManager.user

    override suspend fun saveUser(user: User) {
        preferencesManager.user = user
    }

    override suspend fun initUser(): User? {
        return apiDataSource.getData()?.let { response ->
            saveUser(UserModelMapper.fromNetworkToModel(response.user))
            getUser()
        } ?: getUser()
    }

    override suspend fun setAppTheme(theme: Int) {
        preferencesManager.theme = theme
    }

    override suspend fun getAppTheme(): Int {
        return preferencesManager.theme
    }

    override suspend fun setAppLang(lang: String) {
        preferencesManager.lang = lang
    }

    override fun logOut() = preferencesManager.deleteAll()
}
