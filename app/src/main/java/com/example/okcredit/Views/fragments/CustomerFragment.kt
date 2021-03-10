package com.example.okcredit.Views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.okcredit.Data.local.CustomerEntity
import com.example.okcredit.R
import com.example.okcredit.ViewModel.CustomerViewModel
import com.example.okcredit.ViewModel.CustomerViewModelFactory
import com.example.okcredit.Views.values.OkCreditApplication
import com.example.okcredit.Views.adapters.CustomerAdapter
import com.example.okcredit.Views.interfaces.OnRowItemClicked
import kotlinx.android.synthetic.main.fragment_customer.*


class CustomerFragment : Fragment(), OnRowItemClicked {
    var customerList = mutableListOf<CustomerEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

    }

    private fun initViews(view: View) {

        val appClass = activity?.application as OkCreditApplication

        val repository = appClass.repository
        val viewModelFactory = CustomerViewModelFactory(repository)

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CustomerViewModel::class.java)

        viewModel.getCustomerList().observe(this, Observer {
            if (it != null)
                customerList = it as MutableList<CustomerEntity>
            val linearLayoutManager = LinearLayoutManager(context)
            rv_customerItems.setLayoutManager(linearLayoutManager)
            val customerAdapter = CustomerAdapter(customerList, this)
            rv_customerItems.setAdapter(customerAdapter)
        })

    }

    companion object {
        fun newInstance(): CustomerFragment {
            return CustomerFragment()
        }
    }
}