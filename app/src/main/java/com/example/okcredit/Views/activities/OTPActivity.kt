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
            tvOtp1st.text = "1"
//            tvOtp2nd.text = "2"
//            tvOtp3rd.text = "3"
//            tvOtp4th.text = "4"
//            tvOtp5th.text = "5"
//            tvOtp6th.text = "6"

        }, 3000)
        Handler().postDelayed({
            tvOtp2nd.text = "2"
        }, 3100)
        Handler().postDelayed({
            tvOtp3rd.text = "3"
        }, 3200)
        Handler().postDelayed({
            tvOtp4th.text = "4"
        }, 3300)
        Handler().postDelayed({
            tvOtp5th.text = "5"
        }, 3400)
        Handler().postDelayed({
            tvOtp5th.text = "5"
        }, 3500)
        Handler().postDelayed({
            tvOtp6th.text = "6"
        }, 3600)

        Handler().postDelayed({

            val intent = Intent(this, OTPVerfityActivity::class.java)
            startActivity(intent)

        }, 4500)

    }

    private fun setData() {
        tvMobileNumberOTP.text = SharedPref.readStringData(Const.MOBILE_NUMBER_KEY)
    }
}