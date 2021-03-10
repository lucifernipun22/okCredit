package com.example.okcredit.Data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CustomerEntity::class], version = 1)
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