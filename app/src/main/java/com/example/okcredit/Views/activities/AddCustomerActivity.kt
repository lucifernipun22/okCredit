package com.example.okcredit.Views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
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
    var name = ""
    var number = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_customer)
        val appClass = application as OkCreditApplication
        val repository = appClass.repository
        val viewModelFactory = CustomerViewModelFactory(repository)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CustomerViewModel::class.java)
        btn_cross.setOnClickListener {
            finish()
        }
        btn_save_name.setOnClickListener {
            name = et_name.text.toString()
            btn_save_number.isVisible = true
            tl_number.isVisible = true
            btn_save_name.isVisible = false
            et_number.requestFocus()
        }
        btn_save_number.setOnClickListener {
            number = et_number.text.toString()
            val list = mutableListOf<Transaction>()
//            list.add(Transaction("0","iuyd","2021-11-03 12:30:14","sdfsdf","sdfsdf","2021-11-03 12:30:14"))
            var customerEntity = Customer(name, number, "", list, "", "")
            viewModel.addNewCustomer(customerEntity)
            intent = Intent(this, CustomerTransactionActivity::class.java)
            intent.putExtra("customer", customerEntity)
            startActivity(intent)
            // gotoCustomerTransactionPage()
        }
    }
    /*  private fun gotoCustomerTransactionPage() {
          var customerEntity = CustomerEntity(name, number, "", "")
          viewModel.addNewCustomer(customerEntity)
          intent = Intent(this, CustomerTransactionActivity::class.java)
          startActivity(intent)
      }*/
}