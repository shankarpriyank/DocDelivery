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

    private val _isUserLoggedIn = MutableStateFlow(false)
    val isUserLoggedIn = _isUserLoggedIn.asStateFlow()

    private val _navigate = MutableStateFlow(false)
    val navigate = _navigate.asStateFlow()

    init {
    }

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
        checkSignedInUser()
    }

    fun fetchSignInUser(email: String?, name: String?, profilePhotoUrl: String? = null) {
        val editor = sharedPreferences.edit()
        editor.putString("userName", name)
        editor.putString("userEmail", email)
        editor.putString("userImageUrl", profilePhotoUrl)
        editor.apply()
        Log.e("Name", gsa?.displayName.toString())
        Log.e("Email", gsa?.email.toString())
        Log.e("Url", gsa?.photoUrl.toString())
        viewModelScope.launch {
            _navigate.emit(true)
        }
    }

    private fun checkSignedInUser() {
        if (gsa != null) {
            viewModelScope.launch {
                _isUserLoggedIn.emit(true)
            }

            Log.e("name", gsa.displayName.toString())
            Log.e("email", gsa.email.toString())
            Log.e("url", gsa.photoUrl.toString())
        } else {
            viewModelScope.launch {
                _isUserLoggedIn.emit(false)
            }
            Log.e("No user", "Hey")
        }
    }
}
