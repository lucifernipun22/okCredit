package com.example.okcredit.Views

import android.content.Context
import android.content.SharedPreferences


class SharedPref {

    companion object {

        private var sharedPreferences: SharedPreferences? = null
        private val PREF_NAME = "users"

        fun getSharedPref(context: Context): SharedPreferences {
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            }
            return sharedPreferences!!
        }

        fun writeIntToPref(key: String, value: Int) {
            val editor = sharedPreferences!!.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun writeStringToPref(key: String, value: String) {
            val editor = sharedPreferences!!.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun readIntData(key: String): Int {
            return sharedPreferences!!.getInt(key, 0)
        }

        fun readStringData(key: String): String? {
            return sharedPreferences!!.getString(key, "Hi")
        }

    }


}