package com.priyank.drdelivery.authentication.data

import android.content.SharedPreferences

class UserDetails(
    private val sharedPreferences: SharedPreferences,
) {
    fun isLoggedIn() = sharedPreferences.getBoolean("Logged In", false)
    fun getUserId() = sharedPreferences.getString("userId", "F")
    fun getUserEmail() = sharedPreferences.getString("userEmail", "F")

    fun updateUser(
        id: String?,
        email: String?,
        name: String?,
        profilePhotoUrl: String? = null,
    ) = with(sharedPreferences.edit()) {
        putString("userId", id)
        putString("userName", name)
        putString("userEmail", email)
        putString("userImageUrl", profilePhotoUrl)
        putBoolean("Logged In", true)
        apply()
    }

    fun signOut() = with(sharedPreferences.edit()) {
        putBoolean("Logged In", false)
        apply()
    }
}
