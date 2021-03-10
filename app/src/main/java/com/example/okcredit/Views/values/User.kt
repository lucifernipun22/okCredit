package com.sajorahasan.okcredit.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class User(
    @ColumnInfo
    var name: String = "",
    @ColumnInfo
    var phone: String?,
    @ColumnInfo
    var profileImage: String = "",
    @ColumnInfo
    var businessName: String = "",
    @ColumnInfo
    var businessAddress: String = "",
    @ColumnInfo
    var email: String = "",
    @ColumnInfo
    var businessDesc: String = "",
    @ColumnInfo
    var contactPersonName: String = "",
    @ColumnInfo
    var customers: MutableList<Customer> = mutableListOf(),
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
) : Parcelable