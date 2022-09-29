package com.priyank.drdelivery.authentication

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.priyank.drdelivery.R
import com.priyank.drdelivery.authentication.model.Info
import com.priyank.drdelivery.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject
constructor(
    private val sharedPreferences: SharedPreferences,
    private val gsc: GoogleSignInClient,
) : ViewModel() {

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

    fun fetchSignInUser(
        id: String?,
        email: String?,
        name: String?,
        profilePhotoUrl: String? = null,
        navHostController: NavHostController
    ) {
        Log.e("Value Updated", "Yayy")
        val editor = sharedPreferences.edit()
        editor.putString("userName", name)
        editor.putString("userEmail", email)
        editor.putString("userImageUrl", profilePhotoUrl)
        editor.putBoolean("Logged In", true)
        editor.putString("userId", id)
        editor.apply()
        Log.i("Name", name.toString())
        Log.i("Email", email.toString())
        Log.i("Url", profilePhotoUrl.toString())
        Log.i("Id", id.toString())
        navHostController.popBackStack()
        navHostController.navigate(Screen.Detail.route)
    }
    fun signOutUser() {
        Log.e("Signout", "Hogya")
        val editor = sharedPreferences.edit()
        editor.putBoolean("Logged In", false)
        editor.apply()
        gsc.signOut()
    }
}
