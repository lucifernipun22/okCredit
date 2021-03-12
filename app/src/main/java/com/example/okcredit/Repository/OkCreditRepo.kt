package com.example.okcredit.Repository

import androidx.lifecycle.LiveData
import com.example.okcredit.Data.local.*
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

    fun getTransactionList(): LiveData<List<Transaction>> {
        return okCreditDAO.getTransactionList()
    }

    fun getTransaction(name: String): LiveData<List<Transaction>> {
        return okCreditDAO.getTransaction(name)
    }
    fun addSupplierTransaction(customerEntity: SupplierTransaction) {
        CoroutineScope(Dispatchers.IO).launch {
            okCreditDAO.addSupplierTransaction(customerEntity)
        }
    }

    fun getSupplierTransactionList(): LiveData<List<SupplierTransaction>> {
        return okCreditDAO.getSupplierTransactionList()
    }

    fun getSupplierTransaction(name: String): LiveData<List<SupplierTransaction>> {
        return okCreditDAO.getSupplierTransaction(name)
    }
    fun addSupplier(customerEntity: Supplier) {
        CoroutineScope(Dispatchers.IO).launch {
            okCreditDAO.addSupplier(customerEntity)
        }
    }

    fun getSupplierList(): LiveData<List<Supplier>> {
        return okCreditDAO.getSupplierList()
    }

    fun getSupplier(name: String): LiveData<List<Supplier>> {
        return okCreditDAO.getSupplier(name)
    }
}