package com.example.okcredit.Views.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.CustomerEntity
import com.example.okcredit.Data.local.User
import com.example.okcredit.R
import com.example.okcredit.Views.activities.CustomerTransactionActivity
import com.example.okcredit.Views.activities.HomeActivity
import com.example.okcredit.Views.interfaces.OnRowItemClicked
import kotlinx.android.synthetic.main.customer_item_layout.view.*

class CustomerAdapter(
    private val customer_list: MutableList<Customer>,
    var mlistner: OnRowItemClicked,

    ) : RecyclerView.Adapter<CustomerAdapter.ClassViewHolder>() {
    // private var mListener: O? = null
    private lateinit var user: User

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.customer_item_layout, parent, false)
        return ClassViewHolder(view)
    }

    /*fun setOnItemClickListener(listener: OnRowItemClicked) {
        mlistner = listener
    }*/
    /* interface OnItemClickListener {
         fun onItemClick(view: View, position: Int)
     }
 */
    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        val model = customer_list[position]
      // holder.view.tv_fullname.text = customer_list[position].name
            //holder.view.tv_name.text = customer_list[position].name?.first().toString()
        holder.setData(model, mlistner)
    }

    override fun getItemCount(): Int {
        return customer_list.size
    }

    class ClassViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setData(model: Customer, mlistner: OnRowItemClicked) {
            view.apply {
                tv_fullname.text = model.name
                tv_name.text = model.name?.first().toString()
                if (model.transactions.size > 0) {
                    tv_date.text = model.transactions[0].createdAt.toString()

                    tv_status.text = model.transactions[0].type.toString()
                    tv_price.text = model.transactions[0].amount.toString()
                } else {
                  /*  tv_status.text = model.balanceType
                    tv_price.text = model.balance*/
                }
                constraintLayout.setOnClickListener {
                    mlistner.onItemClick(model)
                }
            }

        }
    }


}