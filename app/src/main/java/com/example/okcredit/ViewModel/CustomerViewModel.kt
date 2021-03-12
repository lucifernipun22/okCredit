package com.example.okcredit.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.CustomerEntity
import com.example.okcredit.Data.local.Supplier
import com.example.okcredit.Data.local.Transaction
import com.example.okcredit.Repository.OkCreditRepo

class CustomerViewModel(val repository: OkCreditRepo) : ViewModel() {

    fun addNewCustomer(customerEntity: Customer) {
        repository.addNewCustomer(customerEntity)
    }

    fun getCustomerList(): LiveData<List<Customer>> {
        return repository.getCustomerList()
    }

    fun customerDetails(name: String): LiveData<List<Customer>> {
        return repository.getCustomer(name)
    }

    fun addTransaction(customerEntity: Transaction) {
        repository.addTransaction(customerEntity)
    }

    fun getTransactionList(): LiveData<List<Transaction>> {
        return repository.getTransactionList()
    }

    fun getTransaction(name: String): LiveData<List<Transaction>> {
        return repository.getTransaction(name)
    }


}