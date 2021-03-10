package com.example.okcredit.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.okcredit.R
import kotlinx.android.synthetic.main.activity_mobile_number.*

class MobileNumberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile_number)

        initialisations()

        btnEnterMobileNumber.setOnClickListener {
            if (dataIsValid()) {
                val mobileNumber = etnMobileNumber.text.toString()
                SharedPref.writeStringToPref(Const.MOBILE_NUMBER_KEY, mobileNumber)
                val intent = Intent(this, OTPActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initialisations() {
        SharedPref.getSharedPref(this)
    }


    private fun dataIsValid(): Boolean {
        var isValid = true

        if (etnMobileNumber.text.toString().length == 10) {
            isValid = true
        }
        if (etnMobileNumber.text.toString().length == 0) {
            etnMobileNumber.error = "Mobile number can't be empty"
            isValid = false
        } else if (etnMobileNumber.text.toString().length > 10 || etnMobileNumber.text.toString().length < 10) {
            etnMobileNumber.error = "Enter valid mobile number"
            isValid = false
        }
        return isValid
    }
}