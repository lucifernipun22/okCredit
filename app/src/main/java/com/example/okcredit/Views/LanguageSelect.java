package com.example.okcredit.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.okcredit.R;

import java.util.Locale;

public class LanguageSelect extends AppCompatActivity {

    private Button btnNext;
    private ImageView btnHindi, btnEnglish;
    private TextView tvWelcome, tvSelectLanguage;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);
        btnNext = findViewById(R.id.btnNext);
        btnHindi = findViewById(R.id.languageHindi);
        btnEnglish = findViewById(R.id.languageEnglish);
        tvWelcome = findViewById(R.id.welcome);
        tvSelectLanguage = findViewById(R.id.selectLanguage);
        /*btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LanguageSelect.this, IntroductionActivity.class);
                startActivity(intent);
            }
        });
        btnHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeLanguage("hn");
            }
        });
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeLanguage("en");
            }
        });



    }
    private void changeLanguage(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getApplicationContext().getResources().updateConfiguration(configuration,getApplicationContext().getResources().getDisplayMetrics());

        tvWelcome.setText(getString(R.string.welcome));
        tvSelectLanguage.setText(getString(R.string.select_your_language));
        btnNext.setText(getString(R.string.next));

    }*/
    }
}