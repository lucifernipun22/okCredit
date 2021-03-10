package com.example.okcredit.Views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.okcredit.Data.local.CustomerEntity
import com.example.okcredit.R
import com.example.okcredit.Views.adapters.CustomerAdapter
import com.example.okcredit.Views.adapters.ViewPagerFragmentAdapter
import com.example.okcredit.Views.interfaces.OnRowItemClicked
import com.example.okcredit.Views.values.Tools
import com.sajorahasan.okcredit.model.Customer
import com.sajorahasan.okcredit.model.User
import com.san.app.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(),OnRowItemClicked {
    private lateinit var pagerAdapter: ViewPagerFragmentAdapter
    private lateinit var customerAdapter: CustomerAdapter
    private lateinit var customerList: MutableList<CustomerEntity>
    private lateinit var user: User
    private val tag: String = "HomeActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setViewPagerAdapter()
        btnAddCustomer.setOnClickListener {
            intent = Intent(this, AddCustomerActivity::class.java)
            startActivity(intent)
        }
        customerList = mutableListOf()
        initializeCustomerRecyclerView()

    }
    private fun initializeCustomerRecyclerView() {
        customerAdapter = CustomerAdapter(customerList, this)
        customerAdapter.setOnItemClickListener(object :
            CustomerAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val c = customerList[position]
                Log.d(tag, "onItemClick pos $position name $c")
                gotoCustomerScreen(view, c)
            }
        })

    }
    private fun gotoCustomerScreen(view: View, c: CustomerEntity) {
        Tools.hideKeyboard(view)

        val intent = Intent(this, CustomerTransactionActivity::class.java)
        intent.putExtra("user", user)
      //  intent.putExtra("customer", c)
        startActivity(intent)
    }

    private fun setViewPagerAdapter() {
        pagerAdapter = ViewPagerFragmentAdapter(supportFragmentManager)
        home_viewPager.setAdapter(pagerAdapter)
        tabLayout.setupWithViewPager(home_viewPager);
    }


}