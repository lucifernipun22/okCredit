package com.example.okcredit.Data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class SupplierTransaction(
    @ColumnInfo(name = "amount")
    var amount: String?="",
    @ColumnInfo(name = "type")
    var type: String?="",
    @ColumnInfo(name = "createdAt")
    var createdAt: String?="",
    @ColumnInfo(name = "note")
    var note: String? = "",
    @ColumnInfo(name = "image")
    var image: String? = "",
    @ColumnInfo(name = "updateAt")
    var updatedAt: String? = "",
    /* @PrimaryKey
     var id: String = UUID.randomUUID().toString()*/
) : Parcelable
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}