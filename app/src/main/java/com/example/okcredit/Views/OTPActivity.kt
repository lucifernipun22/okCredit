package com.example.okcredit.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.okcredit.R
import kotlinx.android.synthetic.main.activity_o_t_p.*
import java.util.*
import kotlin.concurrent.schedule

class OTPActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)

        setData()

        tvEditMobileOTP.setOnClickListener {
            val intent = Intent(this, MobileNumberActivity::class.java)
            startActivity(intent)
        }

        Handler().postDelayed({
            tvOtp1st.text = "0"
            tvOtp2nd.text = "0"
            tvOtp3rd.text = "0"
            tvOtp4th.text = "0"
            tvOtp5th.text = "0"
            tvOtp6th.text = "0"

        }, 5000)

        Handler().postDelayed({

            val intent = Intent(this, BusinessNameActivity::class.java)
            startActivity(intent)

        }, 6000)

    }

    private fun setData() {
        tvMobileNumberOTP.text = SharedPref.readStringData(Const.MOBILE_NUMBER_KEY)
    }
}