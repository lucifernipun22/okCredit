package com.example.okcredit.Views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.okcredit.R
import kotlinx.android.synthetic.main.activity_language_select.*

class LanguageSelect : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_select)



        btnNext.setOnClickListener {
           val intent = Intent(this@LanguageSelect,IntroductionActivity::class.java)
            startActivity(intent)
        }


    }
}