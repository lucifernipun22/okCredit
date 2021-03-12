package com.example.okcredit.Views.values;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import java.util.Locale;

public class Utils {

    public static void setLocale(Context mContext, String lang) {
        Configuration config = new Configuration();
        Log.e("TestValudLan", "999  " + lang);

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        config.locale = locale;
        mContext.getResources().updateConfiguration(config,
                mContext.getResources().getDisplayMetrics());

        Log.e("TAG", "Get local First: : " + Locale.getDefault());

    }
}
