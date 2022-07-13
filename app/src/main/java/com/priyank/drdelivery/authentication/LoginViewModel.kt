package com.priyank.drdelivery.authentication

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androsesdfgasdf
                "Auto-track orders with \n email sync", R.drawable.ic_autotrack
            ),
            Info(
                "Get Realtime Delivery Updates", R.drawable.ic_realtime
            ),
            Info(
                "Your Data is safe, we do everything on the device itself",
                R.drawable.ic_privacy_png
            )

        )
    }

    init {
    }

    fun fetchSignInUser(email: String?, name: String?, profilePhotoUrl: String? = null) {
        Log.e("Value Updated", "Yayy")
        val editor = sharedPreferences.edit()
        editor.putString("userName", name)
        editor.putString("userEmail", email)
        editor.putString("userImageUrl", profilePhotoUrl)
        editor.putBoolean("Logged In", true)
        editor.apply()
        Log.e("Name", name.toString())
        Log.e("Email", email.toString())
        Log.e("Url", profilePhotoUrl.toString())
        viewModelScope.launch {
            _navigateToShipmentScreen.emit(true)
        }
    }
}
