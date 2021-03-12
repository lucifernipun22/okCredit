package com.example.okcredit.Views.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.okcredit.Data.local.SupplierTransaction
import com.example.okcredit.Data.local.Transaction
import com.example.okcredit.R
import com.example.okcredit.Views.values.Tools
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_customer_transaction.view.*
import kotlinx.android.synthetic.main.item_supplier_transaction.view.*


class SupplierTransactionAdapter(
    private val transactions: MutableList<SupplierTransaction>,
    private val context: Context
) :
    RecyclerView.Adapter<SupplierTransactionAdapter.MyViewHolder>() {

    private var mListener: OnItemClickListener? = null

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvContainer: MaterialCardView = view.cvContainer1
        val llBottomContainer: LinearLayout = view.llBottomContainer1
        val tvNote: TextView = view.tvNote1
        val tvAmount: TextView = view.tvAmount1
        val tvDate: TextView = view.tvDate1
        val tvTotalAmount: TextView = view.tvTotalAmount1
        val ivIcon: ImageView = view.ivIcon1

    }

    // Define the mListener interface
    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    // Define the method that allows the parent activity or fragment to define the listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_supplier_transaction, parent, false) as LinearLayout
        return MyViewHolder(view)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val t: SupplierTransaction = transactions[position]
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        if (t.type == "debit") {
            params.apply { gravity = Gravity.END }
            holder.tvAmount.setTextColor(context.resources.getColor(R.color.red))
        } else {
            params.apply { gravity = Gravity.START }
            holder.tvAmount.setTextColor(context.resources.getColor(R.color.colorPrimaryDark))
        }
        holder.cvContainer.layoutParams = params
        holder.llBottomContainer.layoutParams = params

        if (t.note!!.isNotEmpty()) {
            holder.tvNote.text = t.note
            holder.tvNote.visibility = View.VISIBLE
        }

        val amt = Tools.getCurrency2(t.amount!!)

        holder.tvAmount.text = amt

        if (t.image.isNullOrEmpty()) {
            holder.ivIcon.visibility = View.GONE
        }
        holder.tvTotalAmount.text =
            "$amt " + if (t.type == "debit") context.getString(R.string.due) else context.getString(
                R.string.advance
            )

        holder.tvDate.text = Tools.getFormattedTime(t.createdAt, "hh:mm aa")

        holder.cvContainer.setOnClickListener { mListener?.onItemClick(it, position) }

    }


    override fun getItemCount() = transactions.size
}