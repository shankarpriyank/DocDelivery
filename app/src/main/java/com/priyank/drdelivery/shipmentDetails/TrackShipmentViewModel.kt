package com.priyank.drdelivery.shipmentDetails

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.gmail.model.Message
import com.priyank.drdelivery.navigation.Screen
import com.priyank.drdelivery.shipmentDetails.data.remote.GetEmails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackShipmentViewModel @Inject
constructor(
    private val gsaa: GoogleSignInAccount?,
    private val gsc: GoogleSignInClient,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {
    fun signout(navHostController: NavHostController) {
        gsc.signOut()
        val editor = sharedPreferences.edit()
        editor.putBoolean("Logged In", false)
        editor.apply()
        Log.e("Arey", sharedPreferences.getBoolean("Logged In", false).toString())
        navHostController.navigate(Screen.Authentication.route)
        navHostController.popBackStack()
    }

    suspend fun getEmails(): List<Message> {
        val emails = GetEmails().getEmails(gsc.applicationContext, gsaa)
        return emails
    }

    fun call() {

        GlobalScope.launch {
            val emails = async { getEmails() }

            if (emails.await().isEmpty()) {
                Log.i("Empty", "No Relevant Info Found")
            } else {
                for (i in 0 until emails.await().size) {

                    Log.i("Email no $i", emails.await()[i].snippet)
                }
            }
        }
    }
}
