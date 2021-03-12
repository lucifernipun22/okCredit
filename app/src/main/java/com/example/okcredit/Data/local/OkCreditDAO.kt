package com.example.okcredit.Data.local

import androidx.lifecycle.LiveData
import androidx.room.*

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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSupplierTransaction(customerEntity: SupplierTransaction)

    @Query("select * from SupplierTransaction ")
    fun getSupplierTransactionList(): LiveData<List<SupplierTransaction>>

    @Query("select * from SupplierTransaction where amount=:newName")
    fun getSupplierTransaction(newName: String): LiveData<List<SupplierTransaction>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSupplier(customerEntity: Supplier)

    @Query("select * from Supplier ")
    fun getSupplierList(): LiveData<List<Supplier>>

    @Query("select * from Supplier where name=:newName")
    fun getSupplier(newName: String): LiveData<List<Supplier>>


    @Query("SELECT * from User WHERE phone=:phone")
    fun getUser(phone: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM User")
    fun delete()

    @Update
    fun updateUser(user: User): Int

    @Update
    fun updateTrasction(customerEntity: Customer)

}
