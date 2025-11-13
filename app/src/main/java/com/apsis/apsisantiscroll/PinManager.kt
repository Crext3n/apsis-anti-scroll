package com.apsis.apsisantiscroll

import android.content.Context
import android.content.SharedPreferences

class PinManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("pin_prefs", Context.MODE_PRIVATE)

    fun isPinSet(): Boolean = prefs.contains("user_pin")

    fun setPin(pin: String) {
        prefs.edit().putString("user_pin", pin).apply()
    }

    fun isPin(): Boolean {
        val savedPin = prefs.getString("user_pin", "")
        return !savedPin.isNullOrEmpty()  // true-false
    }

    fun clearPin() {
        prefs.edit().remove("user_pin").apply()
    }

    fun verifyPin(pin: String): Boolean {
        return prefs.getString("user_pin", "") == pin
    }
}
