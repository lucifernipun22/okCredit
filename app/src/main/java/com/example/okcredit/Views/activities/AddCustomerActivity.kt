package com.example.okcredit.Views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.CustomerEntity
import com.example.okcredit.Data.local.Transaction
import com.example.okcredit.R
import com.example.okcredit.ViewModel.CustomerViewModel
import com.example.okcredit.ViewModel.CustomerViewModelFactory
import com.example.okcredit.Views.values.OkCreditApplication
import kotlinx.android.synthetic.main.activity_add_customer.*

class AddCustomerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)

        btnSaveCustomerDetails.setOnClickListener {
            val name = etName.text.toString()

            val appClass = application as OkCreditApplication

            val repository = appClass.repository
            val viewModelFactory = CustomerViewModelFactory(repository)

            val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CustomerViewModel::class.java)
            val list = mutableListOf<Transaction>()
            var customerEntity =
                Customer(name,"832","3829",list,"0","Advanec")
            viewModel.addNewCustomer(customerEntity)
            finish()

        }
    }
}