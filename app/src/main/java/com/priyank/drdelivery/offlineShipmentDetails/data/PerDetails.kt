package com.priyank.drdelivery.offlineShipmentDetails.data

import android.content.SharedPreferences

class PerDetails(private val sharedPreferences: SharedPreferences) {
    fun bothPerGranted() = sharedPreferences.getBoolean("BothPer", false)
    fun onlySMSGranted() = sharedPreferences.getBoolean("SMSPer", false)
    fun onlySignInGranted() = sharedPreferences.getBoolean("SignInPer", false)
    fun updatePer(
        onlySMS: Boolean,
        onlySignIn: Boolean,
        bothPer: Boolean
    ) = with(sharedPreferences.edit()) {
        putBoolean("userId", onlySMS)
        putBoolean("userName", onlySignIn)
        putBoolean("BothPer", bothPer)
        apply()
    }
}
