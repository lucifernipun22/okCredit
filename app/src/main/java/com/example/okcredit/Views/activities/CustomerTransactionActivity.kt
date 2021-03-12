package com.example.okcredit.Views.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.okcredit.R
import com.example.okcredit.Views.adapters.CustomerTransactionAdapter
import com.example.okcredit.Views.values.Prefs
import com.example.okcredit.Views.values.Tools
import com.example.okcredit.Data.local.Transaction
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.OkCreditDatabase
import com.example.okcredit.Data.local.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_customer_transaction.*
import kotlinx.android.synthetic.main.customer_item_layout.*
import kotlinx.android.synthetic.main.layout_customer_trans_empty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import kotlin.math.abs

class CustomerTransactionActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val tag: String = "CustomerActivity"
        const val START_ACTIVITY_2_REQUEST_CODE = 2
        const val START_ACTIVITY_3_REQUEST_CODE = 3
    }

    private lateinit var transactions: MutableList<Transaction>
    private lateinit var transactionAdapter: CustomerTransactionAdapter
    private lateinit var db: OkCreditDatabase
    private var disposable: CompositeDisposable? = null
    private lateinit var customer: Customer
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_transaction)
        //Initialize views
        initViews()
        //Set Customer data
        setCustomerData()
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun initViews() {
        //user = intent.getParcelableExtra("user")!!
        customer = intent.getParcelableExtra("customer")!!
        Log.d(tag, "customer===> $customer")
        disposable = CompositeDisposable()
        db = OkCreditDatabase.getRoomDatabase(this)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        initializeTransactionRecyclerView()
        call_btn.setOnClickListener(this)
        btnAcceptPayment.setOnClickListener(this)
        btnGivePayment.setOnClickListener(this)
//        btnBackArrow.setOnClickListener(this)
    }

    private fun initializeTransactionRecyclerView() {
        transactions = customer.transactions.toMutableList()
        transactionAdapter = CustomerTransactionAdapter(transactions, this)
        transactionAdapter.setOnItemClickListener(object :
            CustomerTransactionAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val t = transactions[position]
                gotoTransactionScreen(t)
            }
        })
        rvTransactions.adapter = transactionAdapter
        transactionAdapter.notifyDataSetChanged()
        setupTransactionUI()
        calculateBalance()
    }

    private fun setupTransactionUI() {
        if (transactions.isNullOrEmpty()) {
            rvTransactions.visibility = View.GONE
            totalAmtContainer.visibility = View.GONE
            bottomButtonContainer.visibility = View.GONE
            emptyLayout.visibility = View.VISIBLE
            tvEmptyList.text =
                Tools.getSpannedText(getString(R.string.safe_secure_trans, customer.name))
        } else {
            emptyLayout.visibility = View.GONE
            rvTransactions.visibility = View.VISIBLE
            totalAmtContainer.visibility = View.VISIBLE
            bottomButtonContainer.visibility = View.VISIBLE
        }
    }

    private fun gotoTransactionScreen(t: Transaction) {
        val intent = Intent(this, TransactionActivity::class.java)
        intent.putExtra("customer", customer)
        intent.putExtra("transaction", t)
        startActivityForResult(
            intent,
            START_ACTIVITY_2_REQUEST_CODE
        )
    }

    private fun gotoAddTransactionScreen(type: String) {
        val intent = Intent(this, ReceivedActivity::class.java)
        intent.putExtra("customer", customer)
        intent.putExtra("type", type)
        startActivityForResult(
            intent,
            START_ACTIVITY_3_REQUEST_CODE
        )
    }

    private fun setCustomerData() {
        if (customer.profileImage !== null) {
            Glide.with(this)
                .load(R.drawable.ic_account_125dp)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile)
        } else {
            Glide.with(this)
                .load(R.drawable.ic_account_125dp)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile)
        }
        tvName.text = customer.name
    }

    private fun handleError(t: Throwable?) {
        Log.d(tag, "handleError:  ${t?.localizedMessage}")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.call_btn -> {
                val phoneIntent =
                    Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customer.phone, null))
                startActivity(phoneIntent)
            }
            R.id.btnAcceptPayment -> {
                gotoAddTransactionScreen("credit")
            }
            R.id.btnGivePayment -> {
                gotoAddTransactionScreen("debit")
            }
//            R.id.btnBackArrow -> {
////                gotoHomeActivity()
//            }
        }
    }

//    private fun gotoHomeActivity() {
//        val intent = Intent(this, HomeActivity::class.java)
//        startActivity(intent)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == START_ACTIVITY_3_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                customer = data?.getParcelableExtra("addTransaction")!!
                transactions = customer.transactions.toMutableList()
                Log.d(tag, "customer got it===> $customer")
                updateCustomer()
            }
        } else if (requestCode == START_ACTIVITY_2_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val t: Transaction? = data!!.getParcelableExtra("transaction")
                val delTransaction = data.getBooleanExtra("transaction_del", false)
                if (delTransaction) {
                    transactions.remove(t)
                    customer.transactions = transactions
                }
                Log.d(tag, "Delete Tran $delTransaction & new transaction list $transactions")
                updateCustomer()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun getUserFromDb(): User {
        val phone = Prefs.getString("phone")
        return db.getOkCreditDao().getUser(phone!!)
    }

    private fun updateDb(): User {
        Log.d(tag, "Customer updated successfully inDb1 ${user.customers[0].balance}")
        user.customers.find { it.id == customer.id }?.apply {
            balance = customer.balance
            balanceType = customer.balanceType
            transactions = customer.transactions
        }
        Log.d(tag, "Customer updated successfully inDb2 ${user.customers[0].balance}")
        db.getOkCreditDao().updateUser(user)
        return getUserFromDb()
    }

    private fun updateCustomer() {
        calculateBalance()

//        disposable!!.add(
//            Single.create<User> { e -> e.onSuccess(updateDb()) }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    user = it
//                    initializeTransactionRecyclerView()
//                    Log.d(tag, "Customer updated successfully $user")
//                }) { handleError(it) }
//        )
        CoroutineScope(Dispatchers.Main).launch {
            initializeTransactionRecyclerView()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateBalance() {
        var debitedAmt = 0.0
        var creditAmt = 0.0
        for (t in transactions) {
            if (t.type == "debit") debitedAmt += t.amount?.toDouble()!!
            else creditAmt += t.amount?.toDouble()!!
        }
        val bal = creditAmt - debitedAmt
        val temp = NumberFormat.getInstance().format(abs(bal))
        Log.d(tag, "bal --- $bal  ---temp--- $temp")
        tvTotalBalance.text = "₹ $temp"
        customer.balance = abs(bal).toString()
        if (bal >= 0.0) {
            tvTotalBalance.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            tvBalanceType.text = getString(R.string.advance)
            customer.balanceType = getString(R.string.advance)
        } else {
            tvTotalBalance.setTextColor(ContextCompat.getColor(this, R.color.red))
            tvBalanceType.text = getString(R.string.due)
            customer.balanceType = getString(R.string.due)
        }
        Log.d(tag, "Customer balance --> $customer")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop called from Home")
        disposable?.clear()
    }
}