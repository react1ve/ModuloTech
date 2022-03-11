package com.modulo.data.repository

import com.google.gson.Gson
import com.modulo.data.database.mapper.UserDtoMapper
import com.modulo.data.network.api.DataApi
import com.modulo.data.preferences.SharedPreferencesManager
import com.modulo.domain.api.UserRepositoryApi
import com.modulo.domain.model.User

internal class UserRepository(
    private val preferencesManager: SharedPreferencesManager,
    gson: Gson,
    api: DataApi
) : BaseRepository(gson, api), UserRepositoryApi {

    override suspend fun getUser(): User? = preferencesManager.user

    override suspend fun saveUser(user: User) {
        preferencesManager.user = user
    }

    override suspend fun initUser(): User? {
        return getData()?.let { response ->
            saveUser(UserDtoMapper.fromDto(response.user))
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
