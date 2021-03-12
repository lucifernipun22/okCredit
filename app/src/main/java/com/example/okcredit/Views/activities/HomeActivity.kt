package com.example.okcredit.Views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.OkCreditDAO
import com.example.okcredit.Data.local.Supplier
import com.example.okcredit.Data.local.User
import com.example.okcredit.R
import com.example.okcredit.Repository.OkCreditRepo
import com.example.okcredit.ViewModel.CustomerViewModel
import com.example.okcredit.ViewModel.CustomerViewModelFactory
import com.example.okcredit.Views.adapters.CustomerAdapter
import com.example.okcredit.Views.adapters.ViewPagerFragmentAdapter
import com.example.okcredit.Views.interfaces.OnRowItemClicked
import com.example.okcredit.Views.values.OkCreditApplication
import com.example.okcredit.Views.values.Tools
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.san.app.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.customer_item_layout.view.*


class HomeActivity : BaseActivity(),OnRowItemClicked {

    lateinit var okCreditDao: OkCreditDAO
    lateinit var customerViewModel: CustomerViewModel
    lateinit var okCreditRepository: OkCreditRepo
    private lateinit var customerList: MutableList<Customer>
    private lateinit var customerAdapter: CustomerAdapter
    private lateinit var pagerAdapter: ViewPagerFragmentAdapter
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setViewPagerAdapter()
        initializations()


        /*btnAddCustomer.setOnClickListener {
            intent = Intent(this, AddCustomerActivity::class.java)
            startActivity(intent)
        }*/

        et_search.setOnClickListener {
            startActivity()
        }

        btnAddFilter.setOnClickListener {
            bottomSheetDialog()
        }
        initViews()
    }

    private fun initViews() {
        customerList = mutableListOf()

    }

    private fun bottomSheetDialog() {

        val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val view = LayoutInflater.from(applicationContext).inflate(
            R.layout.bottom_sheet_layout, findViewById(R.id.llBottomConatainer)
        )
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.setCanceledOnTouchOutside(true)
        bottomSheetDialog.show()

        val mradionName = bottomSheetDialog.findViewById<RadioButton>(R.id.radioName)
        val mradionAmount = bottomSheetDialog.findViewById<RadioButton>(R.id.radioAmount)
        val mradioLatest = bottomSheetDialog.findViewById<RadioButton>(R.id.radioLatest)
        val mradioGroup = bottomSheetDialog.findViewById<RadioGroup>(R.id.radioGroup)

        val mbtnApplyBSD = bottomSheetDialog.findViewById<TextView>(R.id.btnApplyBSD)
        val mbtnClear = bottomSheetDialog.findViewById<TextView>(R.id.tvClearBSD)
        val mbtnCancel = bottomSheetDialog.findViewById<TextView>(R.id.btnCancelBSD)
        val mbtnTodayBDS = bottomSheetDialog.findViewById<TextView>(R.id.btnTodayBDS)
        val mbtnPendingSD = bottomSheetDialog.findViewById<TextView>(R.id.btnPendingSD)
        val btnUpcomingBSD = bottomSheetDialog.findViewById<TextView>(R.id.btnUpcomingBSD)

        mradionAmount?.setOnClickListener {
            if (mradionAmount != null) {
                if (!mradionAmount.isChecked) {
                    mradionAmount.isChecked = true
                }
            }

            if (mradionName != null) {
                if (mradionName.isChecked) {
                    mradionName.isChecked = false
                }
            }

            if (mradioLatest != null) {
                if (mradioLatest.isChecked) {
                    mradioLatest.isChecked = false
                }
            }
        }

        mbtnCancel?.setOnClickListener {
            bottomSheetDialog.cancel()
        }

        mbtnApplyBSD?.setOnClickListener {

            if (mradioLatest?.isChecked!!) {
                Toast.makeText(this, "latest", Toast.LENGTH_SHORT).show()
            } else if (mradionName?.isChecked!!) {
                Toast.makeText(this, "Name", Toast.LENGTH_SHORT).show()
            } else if (mradionAmount?.isChecked!!) {
                Toast.makeText(this, "amount", Toast.LENGTH_SHORT).show()

            }
        }
    }
    /*private fun initializeCustomerRecyclerView() {
        customerAdapter = CustomerAdapter(customerList, this)
        customerAdapter.setOnItemClickListener(object :
            CustomerAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val c = customerList[position]
                Log.d("Home", "onItemClick pos $position name $c")
                gotoCustomerScreen(view, c)
            }
        })

    }*/


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
        home_viewPager.setAdapter(pagerAdapter)
        tabLayout.setupWithViewPager(home_viewPager);
    }

    override fun onItemClick(model: Customer) {

    }

    override fun onItem(model: Supplier) {

    }


}