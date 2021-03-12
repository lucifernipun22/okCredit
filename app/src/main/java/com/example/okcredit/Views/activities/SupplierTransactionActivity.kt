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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.okcredit.Data.local.*
import com.example.okcredit.R
import com.example.okcredit.ViewModel.CustomerViewModel
import com.example.okcredit.ViewModel.CustomerViewModelFactory
import com.example.okcredit.ViewModel.SupplierViewModel
import com.example.okcredit.ViewModel.SupplierViewModelFactory
import com.example.okcredit.Views.adapters.CustomerTransactionAdapter
import com.example.okcredit.Views.adapters.SupplierTransactionAdapter
import com.example.okcredit.Views.values.OkCreditApplication
import com.example.okcredit.Views.values.Prefs
import com.example.okcredit.Views.values.Tools
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_customer_transaction.*
import kotlinx.android.synthetic.main.activity_customer_transaction.emptyLayout
import kotlinx.android.synthetic.main.activity_customer_transaction.rvTransactions
import kotlinx.android.synthetic.main.activity_supplier_transaction.*
import kotlinx.android.synthetic.main.layout_customer_trans_empty.*
import kotlinx.android.synthetic.main.layout_customer_trans_empty.tvEmptyList

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import kotlin.math.abs

class SupplierTransactionActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val tag: String = "TransactionActivity"

        const val START_ACTIVITY_2_REQUEST_CODE = 2
        const val START_ACTIVITY_3_REQUEST_CODE = 3
    }


    private lateinit var transaction: MutableList<Transaction>
    private lateinit var transactionAdapter: SupplierTransactionAdapter

    private lateinit var db: OkCreditDatabase
    private var disposable: CompositeDisposable? = null
    var customerList = mutableListOf<Transaction>()
    private lateinit var customer: Supplier
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supplier_transaction)

        //Initialize views
        initViews()

        //Set Customer data
        setCustomerData()

        val appClass = application as OkCreditApplication

        val repository = appClass.repository
        val viewModelFactory = SupplierViewModelFactory(repository)

        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SupplierViewModel::class.java)

        viewModel.getTransactionList().observe(this, Observer {
            if (it != null)
                customerList = it as MutableList<Transaction>
            val linearLayoutManager = LinearLayoutManager(this)
            rvTransactions1.setLayoutManager(linearLayoutManager)
            val customerAdapter = SupplierTransactionAdapter(customerList, this)
            rvTransactions1.setAdapter(customerAdapter)
        })
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun initViews() {
        // user = intent.getParcelableExtra("user")!!
        customer = intent.getParcelableExtra("customer")!!
        Log.d(tag, "customer===> $customer")

        disposable = CompositeDisposable()
        db = OkCreditDatabase.getRoomDatabase(this)

        //cvCardView.setNavigationOnClickListener { onBackPressed() }

        initializeTransactionRecyclerView()

        call_btn1.setOnClickListener(this)
        btnAcceptPayment1.setOnClickListener(this)
        btnGivePayment1.setOnClickListener(this)
    }

    private fun initializeTransactionRecyclerView() {
        transaction = customer.transactions.toMutableList()
        transactionAdapter = SupplierTransactionAdapter(transaction, this)
        transactionAdapter.setOnItemClickListener(object :
            SupplierTransactionAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val t = transaction[position]
                gotoTransactionScreen(t)
            }
        })
        rvTransactions1.adapter = transactionAdapter
        transactionAdapter.notifyDataSetChanged()
        setupTransactionUI()
        calculateBalance()
    }

    private fun setupTransactionUI() {
        if (transaction.isNullOrEmpty()) {
            rvTransactions1.visibility = View.GONE
            totalAmtContainer1.visibility = View.GONE
            bottomButtonContainer1.visibility = View.GONE
            emptyLayout1.visibility = View.VISIBLE
            tvEmptyList.text =
                Tools.getSpannedText(getString(R.string.safe_secure_trans, customer.name))
        } else {
            emptyLayout1.visibility = View.GONE
            rvTransactions1.visibility = View.VISIBLE
            totalAmtContainer1.visibility = View.VISIBLE
            bottomButtonContainer1.visibility = View.VISIBLE
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
        val intent = Intent(this, ReceivedSuppplierActivity::class.java)
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
                .into(ivProfile1)
        } else {
            Glide.with(this)
                .load(R.drawable.ic_account_125dp)
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfile1)
        }
        tvName1.text = customer.name
    }

    private fun handleError(t: Throwable?) {
        Log.d(tag, "handleError:  ${t?.localizedMessage}")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.call_btn1 -> {
                val phoneIntent =
                    Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", customer.phone, null))
                startActivity(phoneIntent)
            }

            R.id.btnAcceptPayment1 -> {
                gotoAddTransactionScreen("credit")
            }
            R.id.btnGivePayment1 -> {
                gotoAddTransactionScreen("debit")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == START_ACTIVITY_3_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                customer = data?.getParcelableExtra("addTransaction")!!
                transaction = customer.transactions.toMutableList()
                Log.d(tag, "customer got it===> $customer")
                updateCustomer()
            }
        } else if (requestCode == START_ACTIVITY_2_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val t: Transaction? = data!!.getParcelableExtra("transaction")
                val delTransaction = data.getBooleanExtra("transaction_del", false)
                if (delTransaction) {
                    transaction.remove(t)
                    customer.transactions = transaction
                }
                Log.d(tag, "Delete Tran $delTransaction & new transaction list $transaction")
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
            transaction = customer.transactions
        }
        Log.d(tag, "Customer updated successfully inDb2 ${user.customers[0].balance}")
        db.getOkCreditDao().updateUser(user)
        return getUserFromDb()
    }

    private fun updateCustomer() {
        calculateBalance()
        CoroutineScope(Dispatchers.Main).launch {
            initializeTransactionRecyclerView()
        }
        /*disposable!!.add(
            Single.create<User> { e -> e.onSuccess(updateDb()) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    user = it
                    initializeTransactionRecyclerView()
                    Log.d(tag, "Customer updated successfully $user")
                }) { handleError(it) }
        )*/
    }

    @SuppressLint("SetTextI18n")
    private fun calculateBalance() {
        var debitedAmt = 0.0
        var creditAmt = 0.0

        for (t in transaction) {
            if (t.type == "debit") debitedAmt += t.amount?.toDouble()!!
            else creditAmt += t.amount?.toDouble()!!
        }

        val bal = creditAmt - debitedAmt
        val temp = NumberFormat.getInstance().format(abs(bal))

        Log.d(tag, "bal --- $bal  ---temp--- $temp")

        tvTotalBalance1.text = "₹ $temp"
        customer.balance = abs(bal).toString()
        if (bal >= 0.0) {
            tvTotalBalance1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            tvBalanceType1.text = getString(R.string.advance)
            customer.balanceType = getString(R.string.advance)
        } else {
            tvTotalBalance1.setTextColor(ContextCompat.getColor(this, R.color.red))
            tvBalanceType1.text = getString(R.string.due)
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