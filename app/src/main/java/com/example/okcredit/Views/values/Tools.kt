package com.example.okcredit.Views.values

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.NumberFormat
import java.text.SimpleDateFormat
import kotlin.math.abs


object Tools {

    // Email validation function
    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Phone validation function
    fun isValidMobile(phone: String): Boolean {
        return phone.length == 10 && android.util.Patterns.PHONE.matcher(phone).matches()
    }

    // Show Keyboard
    fun showKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    // Dismiss Keyboard
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @Suppress("DEPRECATION")
    fun getSpannedText(text: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(text)
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @SuppressLint("SimpleDateFormat")
    fun getFormattedTime(date: String?, p: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val simpleDateFormat = SimpleDateFormat(p)
        return simpleDateFormat.format(parser.parse(date))
    }

    fun getCurrency(value: String): String {
        return "₹ " + NumberFormat.getInstance().format(abs(value.toDouble())).toString()
    }

    fun getCurrency2(value: String): String {
        return "₹ " + NumberFormat.getInstance().format(abs(value.toDouble())).toString()
    }

}