package com.example.okcredit.Data.local

import androidx.room.*
import com.sajorahasan.okcredit.model.User

@Dao
interface UserDao {

    @Query("SELECT * from User WHERE phone=:phone")
    fun getUser(phone: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("DELETE FROM User")
    fun delete()

    @Update
    fun updateUser(user: User): Int
}