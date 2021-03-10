package com.example.okcredit.Views

import android.app.Application
import com.example.okcredit.Data.local.OkCreditDatabase
import com.example.okcredit.Repository.OkCreditRepo

class OkCreditApplication : Application() {
    val okCreditDAO by lazy {
        val roomDatabase = OkCreditDatabase.getRoomDatabase(this)
        roomDatabase.getOkCreditDao()
    }

    //repositoy which is responsible for getting the data and returning to viewmodel
    val repository by lazy {
        OkCreditRepo(okCreditDAO)
    }
}