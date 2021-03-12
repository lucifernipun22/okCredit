package com.example.okcredit.Views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.Supplier
import com.example.okcredit.Data.local.Transaction
import com.example.okcredit.R
import com.example.okcredit.ViewModel.CustomerViewModel
import com.example.okcredit.ViewModel.CustomerViewModelFactory
import com.example.okcredit.ViewModel.SupplierViewModel
import com.example.okcredit.ViewModel.SupplierViewModelFactory
import com.example.okcredit.Views.values.OkCreditApplication
import kotlinx.android.synthetic.main.activity_add_customer.*
import kotlinx.android.synthetic.main.activity_add_customer.etName
import kotlinx.android.synthetic.main.activity_add_supplier_actvity.*

class AddSupplierActvity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_supplier_actvity)
        btnSaveSupplierDetails.setOnClickListener {
            val name = etName1.text.toString()

            val appClass = application as OkCreditApplication

            val repository = appClass.repository
            val viewModelFactory = SupplierViewModelFactory(repository)

            val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SupplierViewModel::class.java)
            val list = mutableListOf<Transaction>()

            var customerEntity =
                Supplier(name,"832","3829",list,"0","Advanec")
            viewModel.addSupplier(customerEntity)
            finish()

        }
    }
}