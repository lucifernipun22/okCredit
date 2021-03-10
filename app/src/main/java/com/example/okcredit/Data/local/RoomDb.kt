package com.example.okcredit.Data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sajorahasan.okcredit.model.Customer
import com.sajorahasan.okcredit.model.User
import com.sajorahasan.okcredit.room.converter.CustomerConverter
import com.sajorahasan.okcredit.room.converter.TransConverter


@Database(entities = [User::class, Customer::class], version = 1, exportSchema = false)
@TypeConverters(value = [CustomerConverter::class, TransConverter::class])
abstract class RoomDb : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDb? = null

        fun getDatabase(context: Context): RoomDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDb::class.java,
                    "kasbonDb"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}