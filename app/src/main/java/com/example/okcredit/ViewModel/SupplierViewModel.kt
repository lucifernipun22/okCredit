package com.example.okcredit.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.okcredit.Data.local.Supplier
import com.example.okcredit.Data.local.SupplierTransaction
import com.example.okcredit.Data.local.Transaction
import com.example.okcredit.Repository.OkCreditRepo

class SupplierViewModel(val repository: OkCreditRepo) : ViewModel() {
    fun addSupplier(customerEntity: Supplier) {
        repository.addSupplier(customerEntity)
    }

    fun getSupplierList(): LiveData<List<Supplier>> {
        return repository.getSupplierList()
    }

    fun getSupplier(name: String): LiveData<List<Supplier>> {
        return repository.getSupplier(name)
    }
    fun addTransaction(customerEntity: SupplierTransaction) {
        repository.addSupplierTransaction(customerEntity)
    }

    fun getTransactionList(): LiveData<List<SupplierTransaction>> {
        return repository.getSupplierTransactionList()
    }

    fun getTransaction(name: String): LiveData<List<SupplierTransaction>> {
        return repository.getSupplierTransaction(name)
    }
}