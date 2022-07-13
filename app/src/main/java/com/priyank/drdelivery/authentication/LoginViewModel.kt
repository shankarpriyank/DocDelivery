package com.priyank.drdelivery.authentication

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.priyank.drdelivery.R
import com.priyank.drdelivery.authentication.model.Info
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(
    private val sharedPreferences: SharedPreferences,
    private val gsa: GoogleSignInAccount?
) : ViewModel() {
    private val _navigateToShipmentScreen = MutableStateFlow(false)
    val navigateToShipmentScreen = _navigateToShipmentScreen.asStateFlow()

    fun data(): List<Info> {

        return listOf(
            Info(
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
