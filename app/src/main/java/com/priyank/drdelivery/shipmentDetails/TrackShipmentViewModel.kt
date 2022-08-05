package com.priyank.drdelivery.shipmentDetails

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.priyank.drdelivery.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrackShipmentViewModel @Inject
constructor(
    private val gsc: GoogleSignInClient,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    fun signout(navHostController: NavHostController) {
        gsc.signOut()
        val editor = sharedPreferences.edit()
        editor.putBoolean("Logged In", false)
        editor.apply()
        Log.e("Arey", sharedPreferences.getBoolean("Logged In", false).toString())
        navHostController.popBackStack()
        navHostController.navigate(Screen.Authentication.route)
    }
}
