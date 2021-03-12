package com.example.okcredit.Data.local

import androidx.room.TypeConverter
import com.example.okcredit.Data.local.Transaction
import com.google.gson.Gson


class SupplierTransConvertor {

    @TypeConverter
    fun listToJson(value: MutableList<SupplierTransaction>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): MutableList<SupplierTransaction>? {

        val objects =
            Gson().fromJson(value, Array<SupplierTransaction>::class.java) as Array<SupplierTransaction>
        return objects.toMutableList()
    }
}