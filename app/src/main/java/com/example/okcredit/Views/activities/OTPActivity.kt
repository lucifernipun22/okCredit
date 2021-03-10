package com.example.okcredit.Views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.okcredit.R
import com.example.okcredit.Views.values.Const
import com.example.okcredit.Views.values.SharedPref
import kotlinx.android.synthetic.main.activity_o_t_p.*

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

        }, 3000)

        Handler().postDelayed({

            val intent = Intent(this, OTPVerfityActivity::class.java)
            startActivity(intent)

        }, 5000)

    }

    private fun setData() {
        tvMobileNumberOTP.text = SharedPref.readStringData(Const.MOBILE_NUMBER_KEY)
    }
}