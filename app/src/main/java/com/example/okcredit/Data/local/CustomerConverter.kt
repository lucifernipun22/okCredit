package com.sajorahasan.okcredit.room.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.example.okcredit.Data.local.Customer

class CustomerConverter {

    @TypeConverter
    fun listToJson(value: MutableList<Customer>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): MutableList<Customer>? {

        val objects =
            Gson().fromJson(value, Array<Customer>::class.java) as Array<Customer>
        return objects.toMutableList()
    }
}