package com.example.okcredit.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.okcredit.Data.local.CustomerEntity
import com.example.okcredit.Repository.OkCreditRepo

class CustomerViewModel(val repository: OkCreditRepo) : ViewModel() {

    fun addNewCustomer(customerEntity: CustomerEntity) {
        repository.addNewCustomer(customerEntity)
    }

    fun getCustomerList():LiveData<List<CustomerEntity>> {
        return repository.getCustomerList()
    }
}