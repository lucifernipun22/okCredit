package com.example.okcredit.Data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.okcredit.Data.local.CustomerEntity

@Dao
interface OkCreditDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNewCustomer(customerEntity: Customer)

    @Query("select * from customer ")
    fun getCustomersList(): LiveData<List<Customer>>

    @Query("select * from customer where name=:newName")
    fun getCustomer(newName: String): LiveData<List<Customer>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTransaction(customerEntity: Transaction)

    @Query("select * from `Transaction` ")
    fun getTransactionList(): LiveData<List<Transaction>>

    @Query("select * from `Transaction` where amount=:newName")
    fun getTransaction(newName: String): LiveData<List<Transaction>>



    @Query("SELECT * from User WHERE phone=:phone")
    fun getUser(phone: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM User")
    fun delete()

    @Update
    fun updateUser(user: User): Int

}
