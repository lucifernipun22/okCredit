package com.example.okcredit.Views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.okcredit.Data.local.OkCreditDAO
import com.example.okcredit.R
import com.example.okcredit.Repository.OkCreditRepo
import com.example.okcredit.ViewModel.CustomerViewModel
import com.example.okcredit.ViewModel.CustomerViewModelFactory
import com.example.okcredit.Views.adapters.ViewPagerFragmentAdapter
import com.example.okcredit.Views.values.OkCreditApplication
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    lateinit var okCreditDao: OkCreditDAO
    lateinit var customerViewModel: CustomerViewModel
    lateinit var okCreditRepository: OkCreditRepo

    private lateinit var pagerAdapter: ViewPagerFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setViewPagerAdapter()
        initializations()

        btnAddCustomer.setOnClickListener {
            intent = Intent(this, AddCustomerActivity::class.java)
            startActivity(intent)
        }

        et_search.setOnClickListener {
            startActivity()
        }

    }

    private fun startActivity() {
        val intent = Intent(this, FindCustomerActivity::class.java)
        startActivity(intent)
    }

    private fun initializations() {
        val myapplication = application as OkCreditApplication
        okCreditDao = myapplication.okCreditDAO
        okCreditRepository = myapplication.repository
        val viewModelFactory = CustomerViewModelFactory(okCreditRepository)
        customerViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CustomerViewModel::class.java)
    }

    private fun setViewPagerAdapter() {
        pagerAdapter = ViewPagerFragmentAdapter(supportFragmentManager)
        home_viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(home_viewPager)
    }

}