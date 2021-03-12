package com.example.okcredit.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.okcredit.Repository.OkCreditRepo

class CustomerViewModelFactory(val repository: OkCreditRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return CustomerViewModel(repository) as T
    }
}