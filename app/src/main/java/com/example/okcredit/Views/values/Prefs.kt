package com.example.okcredit.Views.values

import android.content.Context
import android.content.SharedPreferences


object Prefs {
    private const val NAME = "Prefs"
    private const val MODE = Context.MODE_PRIVATE

    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    fun save(key: String?, value: String?) {
        preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String?): String? {
        return preferences.getString(key, "")
    }

    fun save(key: String?, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String?): Boolean {
        return preferences.getBoolean(key, false)
    }
}