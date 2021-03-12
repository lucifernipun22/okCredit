package com.example.okcredit.Views.values

import android.app.Application
import androidx.lifecycle.ViewModelProviders
import com.example.okcredit.Data.local.OkCreditDatabase
import com.example.okcredit.Repository.OkCreditRepo
import com.example.okcredit.ViewModel.CustomerViewModel
import com.example.okcredit.ViewModel.CustomerViewModelFactory

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