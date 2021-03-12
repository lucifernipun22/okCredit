package com.example.okcredit.Views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.Supplier
import com.example.okcredit.Data.local.User
import com.example.okcredit.R
import com.example.okcredit.Views.interfaces.OnRowItemClicked
import kotlinx.android.synthetic.main.customer_item_layout.view.*
import kotlinx.android.synthetic.main.customer_item_layout.view.constraintLayout
import kotlinx.android.synthetic.main.customer_item_layout.view.tv_fullname
import kotlinx.android.synthetic.main.supplier_item_layout.view.*

class SupplierAdapter(
    private val customer_list: MutableList<Supplier>,
    var mlistner: OnRowItemClicked,

    ) : RecyclerView.Adapter<SupplierAdapter.ClassViewHolder>() {
    // private var mListener: O? = null
    private lateinit var user: User

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.supplier_item_layout, parent, false)
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
        /*holder.view.tv_fullname.text = customer_list[position].name
        holder.view.tv_name.text=customer_list[position].name?.first().toString()*/
        holder.setData(model,mlistner)
    }

    override fun getItemCount(): Int {
        return customer_list.size
    }

    class ClassViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setData(model: Supplier, mlistner: OnRowItemClicked){
            view.apply {
                tv_fullname1.text = model.name
                constraintLayout.setOnClickListener {
                    mlistner.onItem(model)
                }

            }


        }
    }




}