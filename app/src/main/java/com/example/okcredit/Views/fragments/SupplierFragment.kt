package com.example.okcredit.Views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.Supplier
import com.example.okcredit.R
import com.example.okcredit.ViewModel.CustomerViewModel
import com.example.okcredit.ViewModel.CustomerViewModelFactory
import com.example.okcredit.ViewModel.SupplierViewModel
import com.example.okcredit.ViewModel.SupplierViewModelFactory
import com.example.okcredit.Views.activities.AddCustomerActivity
import com.example.okcredit.Views.activities.AddSupplierActvity
import com.example.okcredit.Views.activities.CustomerTransactionActivity
import com.example.okcredit.Views.activities.SupplierTransactionActivity
import com.example.okcredit.Views.adapters.CustomerAdapter
import com.example.okcredit.Views.adapters.SupplierAdapter
import com.example.okcredit.Views.interfaces.OnRowItemClicked
import com.example.okcredit.Views.values.OkCreditApplication
import kotlinx.android.synthetic.main.fragment_customer.*
import kotlinx.android.synthetic.main.fragment_supplier.*
import kotlinx.android.synthetic.main.fragment_supplier.view.*


/*private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"*/


class SupplierFragment : Fragment(), OnRowItemClicked {
    var customerList = mutableListOf<Supplier>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_supplier, container, false)
    }

    companion object {
        fun newInstance(): SupplierFragment {
            return SupplierFragment()

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)


    }

    private fun initViews(view: View) {
        val appClass = activity?.application as OkCreditApplication

        val repository = appClass.repository
        val viewModelFactory = SupplierViewModelFactory(repository)

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SupplierViewModel::class.java)

        viewModel.getSupplierList().observe(this, Observer {
            if (it != null)
                customerList = it as MutableList<Supplier>
            val linearLayoutManager = LinearLayoutManager(context)
            rv_supplierItems.setLayoutManager(linearLayoutManager)
            val customerAdapter = SupplierAdapter(customerList, this)
            rv_supplierItems.setAdapter(customerAdapter)
        })
        view.btnAddSupplier.setOnClickListener {
            val intent = Intent(activity, AddSupplierActvity::class.java)
            startActivity(intent)
        }
    }



    override fun onItemClick(model: Customer) {


    }

    override fun onItem(model: Supplier) {
        gotoCustomerScreen(model)
    }

    private fun gotoCustomerScreen(model: Supplier) {
        //  Tools.hideKeyboard(view)
        val intent = Intent(activity, SupplierTransactionActivity::class.java)
        // intent.putExtra("user", user)
        intent.putExtra("customer", model)
        startActivity(intent)


    }

}