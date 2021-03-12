package com.example.okcredit.Views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.okcredit.R
import kotlinx.android.synthetic.main.activity_o_t_p.*

class OTPVerfityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val intent = Intent(this, SyncCloudDataActivity::class.java)
            startActivity(intent)
        }, 1800)
    }
}