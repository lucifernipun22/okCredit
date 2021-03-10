package com.example.okcredit.Views.values

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class Transaction(
    @ColumnInfo
    var amount: String?,
    @ColumnInfo
    var type: String?,
    @ColumnInfo
    var createdAt: String?,
    @ColumnInfo
    var note: String? = "",
    @ColumnInfo
    var image: String? = "",
    @ColumnInfo
    var updatedAt: String? = "",
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
) : Parcelable