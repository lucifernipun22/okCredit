package com.example.okcredit.Repository

import androidx.lifecycle.LiveData
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.OkCreditDAO
import com.example.okcredit.Data.local.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OkCreditRepo(val okCreditDAO: OkCreditDAO) {
    fun addNewCustomer(customerEntity: Customer) {
        CoroutineScope(Dispatchers.IO).launch {
            okCreditDAO.addNewCustomer(customerEntity)
        }
    }

    fun getCustomerList(): LiveData<List<Customer>> {
        return okCreditDAO.getCustomersList()
    }

    fun getCustomer(name: String): LiveData<List<Customer>> {
        return okCreditDAO.getCustomer(name)
    }

    fun addTransaction(customerEntity: Transaction) {
        CoroutineScope(Dispatchers.IO).launch {
            okCreditDAO.addTransaction(customerEntity)
        }
    }

    fun updateTrasction(customerEntity: Customer) {
        CoroutineScope(Dispatchers.IO).launch {
            okCreditDAO.updateTrasction(customerEntity)
        }
    }

    fun getTransactionList(): LiveData<List<Transaction>> {
        return okCreditDAO.getTransactionList()
    }

    fun getTransaction(name: String): LiveData<List<Transaction>> {
        return okCreditDAO.getTransaction(name)
    }

}