package com.example.okcredit.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.okcredit.R
import kotlinx.android.synthetic.main.activity_business_name.*

class BusinessNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_name)

        btnEnterBusinessName.setOnClickListener {
            if (isDataValid()) {
                SharedPref.writeStringToPref(Const.BUSINESS_NAME, etBusinessName.text.toString())
                Toast.makeText(
                    this,
                    SharedPref.readStringData(Const.BUSINESS_NAME),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun isDataValid(): Boolean {
        if (etBusinessName.text.toString().isEmpty()) {
            etBusinessName.error = "Field can't be empty"
            return false
        }
        return true
    }
}