package com.example.okcredit.Views.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.okcredit.R
import com.example.okcredit.Views.values.Tools
import com.example.okcredit.Data.local.Transaction
import com.san.app.activity.BaseActivity
import com.example.okcredit.Data.local.Customer
import kotlinx.android.synthetic.main.activity_transaction.*


class TransactionActivity : BaseActivity(), View.OnClickListener {
    private var tag: String = "CustomerActivity"

    private lateinit var customer: Customer
    private lateinit var transaction: Transaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        initViews()
        setCustomerData()
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun initViews() {
        customer = intent.getParcelableExtra("customer")!!
        transaction = intent.getParcelableExtra("transaction")!!
        Log.d(tag, "transaction===> $transaction")

        toolbar.setNavigationOnClickListener { onBackPressed() }

        btnWpShare.setOnClickListener(this)
        btnDelete.setOnClickListener(this)
    }

    private fun setCustomerData() {
        if (customer.profileImage !== null) {
            Glide.with(this)
                .load(Uri.parse(customer.profileImage))
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile)
        } else {
            Glide.with(this)
                .load(R.drawable.ic_account_125dp)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile)
        }
        if (!transaction.image.isNullOrBlank()) {
            Glide.with(this)
                .load(transaction.image)
                .into(ivReceipt)
        }
        tvName.text = customer.name
        tvAmount.text = Tools.getCurrency(transaction.amount!!)
        tvCalDate.text = Tools.getFormattedTime(transaction.createdAt, "dd MMM yyyy")
        tvCreatedAt.text = Tools.getFormattedTime(transaction.createdAt, "dd MMM yyyy, hh:mm a")
        if (transaction.note!!.isNotEmpty()) {
            noteContainer.visibility = View.VISIBLE
            tvNote.text = transaction.note
        }

        if (transaction.type == "debit") {
            tvAmount.setTextColor(resources.getColor(R.color.red))
            tvDelete.text = getString(R.string.delete_credit)
        } else {
            tvAmount.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            tvDelete.text = getString(R.string.delete_payment)
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnWpShare -> {
                Toast.makeText(this, "WIP", Toast.LENGTH_SHORT).show()
            }
            R.id.btnDelete -> {
                showDeleteDialog()
            }
        }
    }

    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        val title = if (transaction.type == "debit") {
            getString(R.string.delete_credit)
        } else {
            getString(R.string.delete_payment)
        }
        builder.setTitle(title)
            .setMessage(R.string.delete_msg)
            .setPositiveButton(R.string.delete) { dialog, _ ->
                dialog.dismiss()
                val intent = Intent().apply {
                    putExtra("transaction", transaction)
                    putExtra("transaction_del", true)
                }
                setResult(Activity.RESULT_OK, intent)
                onBackPressed()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
        builder.create()
        builder.show()
    }

    private fun sendDataBackToHomeActivity() {
        val intent = Intent().apply { putExtra("transaction", transaction) }
        setResult(Activity.RESULT_OK, intent)
    }

}