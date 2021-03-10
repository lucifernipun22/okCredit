package com.example.okcredit.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.okcredit.R
import com.example.okcredit.Views.activities.AddCustomerActivity
import com.example.okcredit.Views.activities.HomeActivity
import kotlinx.android.synthetic.main.activity_business_name.*

class BusinessNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_name)

        btnEnterBusinessName.setOnClickListener {
            if (isDataValid()) {
                gotoHomePage()
            }
        }
    }

    private fun gotoHomePage() {
        intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun isDataValid(): Boolean {
        if (etBusinessName.text.toString().isEmpty()) {
            etBusinessName.error = "Field can't be empty"
            return false
        }
        return true
    }
}