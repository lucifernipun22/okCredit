package com.example.okcredit.Views.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import com.example.okcredit.R
import com.san.app.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_language_select.*
import java.util.*

class LanguageSelect : BaseActivity() {
    var language = "en"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_select)

        languageHindi.setOnClickListener {
            language = "hi"
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
            val intent = Intent(this@LanguageSelect, IntroductionActivity::class.java)
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            val intent = Intent(this@LanguageSelect, IntroductionActivity::class.java)
            startActivity(intent)

        }
        languageEnglish.setOnClickListener {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
            val intent = Intent(this@LanguageSelect, IntroductionActivity::class.java)
            startActivity(intent)
        }
    }

}