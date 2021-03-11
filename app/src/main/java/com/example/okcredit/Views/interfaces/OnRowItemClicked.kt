package com.example.okcredit.Views.interfaces

import android.view.View
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Views.adapters.CustomerAdapter

interface OnRowItemClicked {
    fun onItemClick( model: Customer)

}