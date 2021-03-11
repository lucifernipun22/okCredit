package com.example.okcredit.Views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.utils.Utils
import com.example.okcredit.R
import com.example.okcredit.Views.values.Prefs
import com.san.app.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_language_select.*

class LanguageSelect : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_select)



        btnNext.setOnClickListener {
            val intent = Intent(this@LanguageSelect, IntroductionActivity::class.java)
            startActivity(intent)
        }
    }




}