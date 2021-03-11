package com.example.okcredit.Data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class User(
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "phone")
    var phone: String?="",
    @ColumnInfo(name = "profileName")
    var profileImage: String = "",
    @ColumnInfo(name = "businessName")
    var businessName: String = "",
    @ColumnInfo(name = "busineeAddress")
    var businessAddress: String = "",
    @ColumnInfo(name = "email")
    var email: String = "",
    @ColumnInfo(name = "businessDesc")
    var businessDesc: String = "",
    @ColumnInfo(name = "contactPersonName")
    var contactPersonName: String = "",
    @ColumnInfo(name = "customers")
    var customers: MutableList<Customer> = mutableListOf(),
    /*@PrimaryKey
    var id: String = UUID.randomUUID().toString()*/
) : Parcelable
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}