package com.example.okcredit.Data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.okcredit.Data.local.Transaction
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class Customer(
    @ColumnInfo(name = "name")
    var name: String?="",
    @ColumnInfo(name = "phone")
    var phone: String?="",
    @ColumnInfo(name = "profile")
    var profileImage: String?="",
    @ColumnInfo(name = "transaction")
    var transactions: MutableList<Transaction> = mutableListOf(),
    @ColumnInfo(name = "balance")
    var balance: String = "0",
    @ColumnInfo(name = "balanceType")
    var balanceType: String = "Advance",
   /* @PrimaryKey
    var id: String = UUID.randomUUID().toString()*/
) : Parcelable
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}