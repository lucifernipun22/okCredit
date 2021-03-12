package com.example.okcredit.Data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customerEntity")
data class CustomerEntity(
    @ColumnInfo(name = "name") var name: String,
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null

}