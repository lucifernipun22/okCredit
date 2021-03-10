package com.example.okcredit.Views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.okcredit.R

class SyncCloudDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sync_cloud_data)
        Handler().postDelayed({
            val intent = Intent(this, BusinessNameActivity::class.java)
            startActivity(intent)
        }, 3000)
    }
}