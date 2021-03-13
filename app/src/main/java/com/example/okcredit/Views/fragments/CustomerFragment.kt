package com.example.okcredit.Views.fragments

import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.Supplier
import com.example.okcredit.R
import com.example.okcredit.ViewModel.CustomerViewModel
import com.example.okcredit.ViewModel.CustomerViewModelFactory
import com.example.okcredit.Views.activities.HomeActivity
import com.example.okcredit.Views.activities.AddCustomerActivity
import com.example.okcredit.Views.activities.CustomerTransactionActivity
import com.example.okcredit.Views.adapters.CustomerAdapter
import com.example.okcredit.Views.interfaces.OnRowItemClicked
import com.example.okcredit.Views.values.Const
import com.example.okcredit.Views.values.OkCreditApplication
import com.example.okcredit.Views.values.SharedPref
import kotlinx.android.synthetic.main.activity_home.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_add_customer.*
import kotlinx.android.synthetic.main.fragment_customer.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import java.util.Collections.*
import kotlinx.android.synthetic.main.fragment_customer.view.*


class CustomerFragment : Fragment(), OnRowItemClicked {
    var customerList = mutableListOf<Customer>()
    lateinit var homeActivity: HomeActivity
    lateinit var customerAdapter: CustomerAdapter
    lateinit var viewModel: CustomerViewModel
    var customerList2 = mutableListOf<Customer>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer, container, false)

        return view
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
        Log.d("tag", "in Resume ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

    }

    private fun initViews(view: View) {

        val appClass = activity?.application as OkCreditApplication

        val repository = appClass.repository
        val viewModelFactory = CustomerViewModelFactory(repository)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CustomerViewModel::class.java)

        viewModel.getCustomerList().observe(viewLifecycleOwner, Observer {
            if (it != null)
                customerList = it as MutableList<Customer>
            if (customerList.size > 1) {
//                homeActivity.btnAddFilter.visibility = VISIBLE
//                homeActivity.cardView2.visibility= VISIBLE
                btnFilterfRa.visibility = VISIBLE
                cardViewCustomer.visibility = VISIBLE
            }
            customerList = it as MutableList<Customer>
            val linearLayoutManager = LinearLayoutManager(context)
            rv_customerItems.setLayoutManager(linearLayoutManager)
            customerAdapter = CustomerAdapter(customerList, this)
            rv_customerItems.setAdapter(customerAdapter)
        })

        view.btnAddCustomerfRA.setOnClickListener {
            val intent = Intent(activity, AddCustomerActivity::class.java)
            startActivity(intent)
        }
        view.btnFilterfRa.setOnClickListener {
            bottomSheetDialog(view)
        }


    }

    private fun bottomSheetDialog(view: View) {

        val bottomSheetDialog = BottomSheetDialog(view.context, R.style.BottomSheetDialogTheme)
        val view = LayoutInflater.from(view.context).inflate(
            R.layout.bottom_sheet_layout, view.findViewById(R.id.llBottomConatainer)
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
                EventBus.getDefault().post(NewTodoItemEvent(3))
                bottomSheetDialog.cancel()
            } else if (mradionAmount?.isChecked!!) {
                EventBus.getDefault().post(NewTodoItemEvent(2))
                bottomSheetDialog.cancel()
            } else if (mradionName?.isChecked!!) {
                EventBus.getDefault().post(NewTodoItemEvent(1))
                bottomSheetDialog.cancel()
            }
        }
    }

    companion object {
        fun newInstance(): CustomerFragment {
            return CustomerFragment()
        }
    }

    override fun onItemClick(model: Customer) {
        gotoCustomerScreen(model)

    }

    override fun onItem(model: Supplier) {

    }

    private fun gotoCustomerScreen(model: Customer) {
        //  Tools.hideKeyboard(view)
        val intent = Intent(activity, CustomerTransactionActivity::class.java)
        // intent.putExtra("user", user)
        intent.putExtra("customer", model)
        startActivity(intent)


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun newToDoItems(newToDoItemEvent: NewTodoItemEvent) {
        Log.d("tag", "" + newToDoItemEvent.id)
        if (newToDoItemEvent.id == 1) {

            sort(customerList, object : Comparator<Customer?> {
                override fun compare(o1: Customer?, o2: Customer?): Int {
                    return o1?.name!!.compareTo(o2?.name!!)
                }
            })
            customerAdapter.notifyDataSetChanged()
        }
    }

}