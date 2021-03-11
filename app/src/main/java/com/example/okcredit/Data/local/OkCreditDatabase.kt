package com.example.okcredit.Data.local

import android.content.Context
import androidx.room.*
import com.sajorahasan.okcredit.room.converter.CustomerConverter
import com.sajorahasan.okcredit.room.converter.TransConverter

@Database(entities = [CustomerEntity::class,Customer::class,Transaction::class,User::class], version = 1,exportSchema = false)
@TypeConverters(value = [CustomerConverter::class, TransConverter::class])
abstract class OkCreditDatabase : RoomDatabase() {

    abstract fun getOkCreditDao(): OkCreditDAO

    companion object {

        private var INSTANCE: OkCreditDatabase? = null

        fun getRoomDatabase(context: Context): OkCreditDatabase {

            if (INSTANCE == null) {

                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    OkCreditDatabase::class.java,
                    "OkCredit_database"
                )

                builder.fallbackToDestructiveMigration()
                INSTANCE = builder.build()
                return INSTANCE!!
            } else {
                return INSTANCE!!
            }

        }

    }
}