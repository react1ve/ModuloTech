package com.modulo.data.preferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.modulo.data.Constants
import com.modulo.domain.model.User

internal class SharedPreferencesManager(private val preferences: SharedPreferences, gson: Gson) {

    var user by preferences.custom<User>(gson)
    var theme by preferences.int(-1)
    var lang by preferences.string(Constants.ENG)
    fun deleteAll() {
        preferences.edit().clear().apply()
    }
}
