package com.example.okcredit.Views.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.okcredit.Data.local.Customer
import com.example.okcredit.Data.local.OkCreditDatabase
import com.example.okcredit.Data.local.User
import com.example.okcredit.R
import com.example.okcredit.Views.values.Prefs
import com.example.okcredit.Views.values.Tools
import com.san.app.activity.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_account_customer.*
import kotlinx.android.synthetic.main.view_action_bar.*
import java.text.NumberFormat
import kotlin.math.abs

class AccountCustomerActivity: BaseActivity() {
    private lateinit var list: MutableList<User>
    private var TAG: String = "AccountActivity"

    private lateinit var user: Customer
    private lateinit var db: OkCreditDatabase
    private var disposable: CompositeDisposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_customer)

        //Initialize views
        initViews()

    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        disposable = CompositeDisposable()
        db = OkCreditDatabase.getRoomDatabase(this)


    }

    private fun setProfileData() {
       // user = intent.getParcelableExtra("user")!!
        //Log.d(TAG, "User===> $user")
         list = mutableListOf<User>()
        user = intent.getParcelableExtra("customer")!!
       if (user.name!!.isEmpty()) supportActionBar?.title = user.phone
       else supportActionBar?.title = user.name


       customerCount.text = list[0].customers.size.toString()

        balance.text = Tools.getCurrency("0")

        calculateBalance()

        val locale = Prefs.getString("locale")
        if (locale == "hi") {
            activeLanguage.text = "Hindi"
        } else {
            activeLanguage.text = "English"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateBalance() {
        var debitedAmt = 0.0
        var creditAmt = 0.0

        for (c in list) {
            if (c.customers[0].balance == getString(R.string.due)) debitedAmt += c.customers[0].balanceType.toDouble()
            else creditAmt += c.customers[0].balance.toDouble()
        }

        Log.d(CustomerTransactionActivity.tag, "creditAmt --- $creditAmt  ---debitedAmt--- $debitedAmt")
        val bal = creditAmt - debitedAmt
        val temp = NumberFormat.getInstance().format(abs(bal))

        Log.d(CustomerTransactionActivity.tag, "bal --- $bal  ---temp--- $temp")

        balance.text = "₹ $temp"

        if (bal >= 0.0) balance.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        else balance.setTextColor(ContextCompat.getColor(this, R.color.red))
    }



    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Called on Account")
        // set profile data
        setProfileData()
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onStop() {
        super.onStop()
        disposable?.clear()
    }
}