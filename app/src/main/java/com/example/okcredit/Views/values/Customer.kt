package com.sajorahasan.okcredit.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.okcredit.Views.values.Transaction
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class Customer(
    @ColumnInfo
    var name: String?,
    @ColumnInfo
    var phone: String?,
    @ColumnInfo
    var profileImage: String?,
    @ColumnInfo
    var transactions: MutableList<Transaction> = mutableListOf(),
    @ColumnInfo
    var balance: String = "0",
    @ColumnInfo
    var balanceType: String = "Advance",
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
) : Parcelable