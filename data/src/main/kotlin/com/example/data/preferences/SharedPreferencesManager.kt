package com.example.data.preferences

import android.content.SharedPreferences
import com.example.data.Constants
import com.example.domain.model.User
import com.google.gson.Gson

internal class SharedPreferencesManager(private val preferences: SharedPreferences, gson: Gson) {

    var user by preferences.custom<User>(gson)
    var theme by preferences.int(-1)
    var lang by preferences.string(Constants.ENG)
    fun deleteAll() {
        preferences.edit().clear().apply()
    }
}
