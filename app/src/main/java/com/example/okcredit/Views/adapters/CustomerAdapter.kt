package com.example.okcredit.Views.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.okcredit.Data.local.CustomerEntity
import com.example.okcredit.R
import com.example.okcredit.Views.activities.CustomerTransactionActivity
import com.example.okcredit.Views.activities.HomeActivity
import com.example.okcredit.Views.interfaces.OnRowItemClicked
import kotlinx.android.synthetic.main.customer_item_layout.view.*

class CustomerAdapter(
    private val customer_list: MutableList<CustomerEntity>,
    val listner: OnRowItemClicked
) : RecyclerView.Adapter<CustomerAdapter.ClassViewHolder>() {
    private var mListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.customer_item_layout, parent, false)
        return ClassViewHolder(view)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onBindViewHolder(holder: ClassViewHolder, position: Int) {
        holder.view.tv_fullname.text = customer_list[position].name
        holder.view.tv_name.text=customer_list[position].name.first().toString()



    }

    override fun getItemCount(): Int {
        return customer_list.size
    }

    class ClassViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


    }


}