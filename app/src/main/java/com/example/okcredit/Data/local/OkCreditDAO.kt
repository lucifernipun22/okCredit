package com.example.okcredit.Data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.okcredit.Data.local.CustomerEntity

@Dao
interface OkCreditDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewCustomer(customerEntity: CustomerEntity)

    @Query("select * from tasks ")
    fun getCustomersList(): LiveData<List<CustomerEntity>>


}
