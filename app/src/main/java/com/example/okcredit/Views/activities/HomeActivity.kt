package com.example.okcredit.Views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.okcredit.R
import com.example.okcredit.Views.adapters.ViewPagerFragmentAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var pagerAdapter: ViewPagerFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setViewPagerAdapter()
        btnAddCustomer.setOnClickListener {
            intent = Intent(this, AddCustomerActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setViewPagerAdapter() {
        pagerAdapter = ViewPagerFragmentAdapter(supportFragmentManager)
        home_viewPager.setAdapter(pagerAdapter)
        tabLayout.setupWithViewPager(home_viewPager);
    }
}