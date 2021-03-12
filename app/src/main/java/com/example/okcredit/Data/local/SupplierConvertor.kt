package com.example.okcredit.Data.local

import androidx.room.TypeConverter
import com.google.gson.Gson

class SupplierConvertor {

    @TypeConverter
    fun listToJson(value: MutableList<Supplier>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): MutableList<Supplier>? {

        val objects =
            Gson().fromJson(value, Array<Supplier>::class.java) as Array<Supplier>
        return objects.toMutableList()
    }
}