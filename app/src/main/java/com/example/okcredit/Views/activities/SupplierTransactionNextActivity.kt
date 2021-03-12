package com.example.okcredit.Views.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.Supplier
import com.example.okcredit.Data.local.SupplierTransaction
import com.example.okcredit.Data.local.Transaction
import com.example.okcredit.R
import com.example.okcredit.Views.values.Tools
import com.san.app.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_customer_transaction2.*
import kotlinx.android.synthetic.main.activity_transaction.*
import kotlinx.android.synthetic.main.activity_transaction.btnWpShare
import kotlinx.android.synthetic.main.activity_transaction.toolbar

class SupplierTransactionNextActivity : BaseActivity(), View.OnClickListener {
    private var tag: String = "CustomerActivity"

    private lateinit var customer: Supplier
    private lateinit var transaction: SupplierTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_transaction2)

        initViews()
        setCustomerData()
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun initViews() {
        customer = intent.getParcelableExtra("customer")!!
        transaction = intent.getParcelableExtra("transaction")!!
        Log.d(tag, "transaction===> $transaction")

        toolbar1.setNavigationOnClickListener { onBackPressed() }

        btnWpShare1.setOnClickListener(this)
        btnDelete1.setOnClickListener(this)
    }

    private fun setCustomerData() {
        if (customer.profileImage !== null) {
            Glide.with(this)
                .load(Uri.parse(customer.profileImage))
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile1)
        } else {
            Glide.with(this)
                .load(R.drawable.ic_account_125dp)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile1)
        }
        if (!transaction.image.isNullOrBlank()) {
            Glide.with(this)
                .load(transaction.image)
                .into(ivReceipt1)
        }
        tvName1.text = customer.name
        tvAmount1.text = Tools.getCurrency2(transaction.amount!!)
        tvCalDate1.text = Tools.getFormattedTime(transaction.createdAt, "dd MMM yyyy")
        tvCreatedAt1.text = Tools.getFormattedTime(transaction.createdAt, "dd MMM yyyy, hh:mm a")
        if (transaction.note!!.isNotEmpty()) {
            noteContainer1.visibility = View.VISIBLE
            tvNote1.text = transaction.note
        }

        if (transaction.type == "debit") {
            tvAmount1.setTextColor(resources.getColor(R.color.red))
            tvDelete1.text = getString(R.string.delete_credit)
        } else {
            tvAmount1.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            tvDelete1.text = getString(R.string.delete_payment)
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnWpShare1 -> {
                Toast.makeText(this, "WIP", Toast.LENGTH_SHORT).show()
            }
            R.id.btnDelete1 -> {
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