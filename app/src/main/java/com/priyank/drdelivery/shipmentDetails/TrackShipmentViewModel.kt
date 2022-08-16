package com.priyank.drdelivery.shipmentDetails

import android.accounts.Account
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.GmailScopes
import com.google.api.services.gmail.model.Message
import com.priyank.drdelivery.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        navHostController.popBackStack()
        navHostController.navigate(Screen.Authentication.route)
    }

    suspend fun getEmails(
        applicationContext: Context = gsc.applicationContext,
        gsa: GoogleSignInAccount? = gsaa
    ): List<Message> {

        val messageList: List<Message> = listOf()
        val credential = GoogleAccountCredential.usingOAuth2(
            applicationContext, listOf(GmailScopes.GMAIL_READONLY)
        )
            .setBackOff(ExponentialBackOff())
            .setSelectedAccount(
                Account(
                    gsa!!.email, "Dr.Delivery"

                )
            )

        val service = Gmail.Builder(
            NetHttpTransport(), AndroidJsonFactory.getDefaultInstance(), credential
        )
            .setApplicationName("DocDelivery")
            .build()

        GlobalScope.launch {
            try {
                val emailList =
                    withContext(Dispatchers.IO) {
                        service.users().messages()?.list("me")?.execute()
                    }
                Log.e("Size", emailList!!.messages.size.toString())

                for (i in 0 until emailList!!.messages.size) {
                    messageList.toMutableList().add(
                        withContext(Dispatchers.IO) {
                            service.users().messages().get("me", emailList.messages[i].id)
                                .setFormat("Full").execute()
                        }

                    )
                }
            } catch (e: UserRecoverableAuthIOException) {
                e.printStackTrace()
            }
        }.onJoin

        return messageList
    }
}
