package com.example.data.preferences

import android.content.SharedPreferences
import com.example.domain.model.User
import com.google.gson.Gson

internal class SharedPreferencesManager(private val preferences: SharedPreferences, gson: Gson) {

    var user by preferences.custom<User>(gson)

    fun deleteAll() {
        preferences.edit().clear().apply()
    }
}
