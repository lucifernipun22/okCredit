package com.example.okcredit.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.okcredit.Repository.OkCreditRepo

class SupplierViewModelFactory(val repository: OkCreditRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return SupplierViewModel(repository) as T
    }
}