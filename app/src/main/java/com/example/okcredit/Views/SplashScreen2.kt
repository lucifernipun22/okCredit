package com.example.okcredit.Views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.okcredit.R
import kotlinx.android.synthetic.main.activity_splash_screen2.*

class SplashScreen2 : AppCompatActivity() {
    var topAnim: Animation? = null
    var bottomAnim: Animation? = null
    var image: ImageView? = null
    var text: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen2)
        topAnim = AnimationUtils.loadAnimation(this, R.anim.left_to_right)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.right_to_left)
        image = findViewById<ImageView>(R.id.ivLogo)
        text = findViewById<TextView>(R.id.tvOkCredit)
        ivLogo.setAnimation(topAnim)
        tvOkCredit.setAnimation(bottomAnim)
        Handler().postDelayed({
            val i = Intent(this@SplashScreen2, LanguageSelect::class.java)
            startActivity(i)
        }, 2500)



    }
}