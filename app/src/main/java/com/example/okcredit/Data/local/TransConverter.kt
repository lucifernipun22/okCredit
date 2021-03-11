package com.sajorahasan.okcredit.room.converter

import androidx.room.TypeConverter
import com.example.okcredit.Data.local.Transaction
import com.google.gson.Gson


class TransConverter {

    @TypeConverter
    fun listToJson(value: MutableList<Transaction>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): MutableList<Transaction>? {

        val objects =
            Gson().fromJson(value, Array<Transaction>::class.java) as Array<Transaction>
        return objects.toMutableList()
    }
}