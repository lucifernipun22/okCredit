package com.example.okcredit.Repository

import androidx.lifecycle.LiveData
import com.example.okcredit.Data.local.CustomerEntity
import com.example.okcredit.Data.local.OkCreditDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OkCreditRepo(val okCreditDAO: OkCreditDAO) {
    fun addNewCustomer(customerEntity: CustomerEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            okCreditDAO.addNewCustomer(customerEntity)
        }
    }

    fun getCustomerList(): LiveData<List<CustomerEntity>> {
        return okCreditDAO.getCustomersList()
    }

}