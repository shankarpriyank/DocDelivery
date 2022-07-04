package com.priyank.drdelivery.authentication

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.priyank.drdelivery.R
import com.priyank.drdelivery.authentication.model.Info
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(
    private val sharedPreferences: SharedPreferences,
    private val gsa: GoogleSignInAccount?
) : ViewModel() {

    private val _triggerevent = MutableLiveData(false)
    val triggerEvent: LiveData<Boolean> = _triggerevent
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
    fun signIn() {
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
        _triggerevent.value = true
    }

    private fun checkSignedInUser() {
        if (gsa != null) {
            _triggerevent.value = true
        }
    }
}
