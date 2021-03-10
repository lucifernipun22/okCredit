package com.example.okcredit.Data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.okcredit.Data.local.CustomerEntity

@Dao
interface OkCreditDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewCustomer(customerEntity: CustomerEntity)

    @Query("select * from customer ")
    fun getCustomersList(): LiveData<List<CustomerEntity>>

    @Query("select * from customer where name=:newName")
    fun getCustomer(newName: String): LiveData<List<CustomerEntity>>

//    @Query("update customer SET title=:newTitle, type =:newType, amount=:newAmount where id=:newid")
//    fun updateTable(newTitle: String, newType: String, newAmount: String, newid: Int)

}
