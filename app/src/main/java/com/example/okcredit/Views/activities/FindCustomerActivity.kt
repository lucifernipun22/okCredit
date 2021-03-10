package com.example.okcredit.Views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.okcredit.Data.local.CustomerEntity
import com.example.okcredit.R
import com.example.okcredit.ViewModel.CustomerViewModel
import com.example.okcredit.ViewModel.CustomerViewModelFactory
import com.example.okcredit.Views.adapters.CustomerAdapter
import com.example.okcredit.Views.interfaces.OnRowItemClicked
import com.example.okcredit.Views.values.OkCreditApplication
import kotlinx.android.synthetic.main.activity_find_customer.*


class FindCustomerActivity : AppCompatActivity(), OnRowItemClicked {

    var customerList = mutableListOf<CustomerEntity>()
    lateinit var customerViewModel: CustomerViewModel
    lateinit var customerAdapter: CustomerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_customer)

        initViews()

        rvCustomerList.apply {
            layoutManager = LinearLayoutManager(this@FindCustomerActivity)
            adapter = customerAdapter
        }

        etSearchCustomer.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (etSearchCustomer.text.length > 4) {
                    customerViewModel.customerDetails(etSearchCustomer.text.toString())
                        .observe(this@FindCustomerActivity, Observer {
                            if (it != null)
                                customerList.clear()
                            customerList.addAll(it)
                            customerAdapter.notifyDataSetChanged()
                        })
                }
            }
        })
    }

    private fun initViews() {
        val appClass = application as OkCreditApplication
        val repository = appClass.repository
        val viewModelFactory = CustomerViewModelFactory(repository)
        customerAdapter = CustomerAdapter(customerList, this@FindCustomerActivity)

        customerViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CustomerViewModel::class.java)

        customerViewModel.getCustomerList().observe(this, Observer {
            if (it != null)
                customerList.clear()
            customerList.addAll(it)
            customerAdapter.notifyDataSetChanged()
        })

        iBtnCross.setOnClickListener {
            startActivity()
        }
        iBtnCross.setOnClickListener {
            startActivity()
        }

    }

    private fun startActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}