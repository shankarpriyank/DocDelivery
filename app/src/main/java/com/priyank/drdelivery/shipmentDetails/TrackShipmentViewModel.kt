package com.priyank.drdelivery.shipmentDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.gmail.model.Message
import com.priyank.drdelivery.authentication.data.UserDetails
import com.priyank.drdelivery.shipmentDetails.data.remote.GetEmails
import com.priyank.drdelivery.shipmentDetails.domain.ParseEmail
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
    private val userDetails: UserDetails,
) : ViewModel() {

    suspend fun getEmails(): List<Message> {

        val emails = GetEmails().getEmails(
            gsc.applicationContext,
            userDetails.getUserId(),
            userDetails.getUserEmail(),
        )
        return emails
    }

    fun call() {

        GlobalScope.launch {
            val emails = async { getEmails() }

            if (emails.await().isEmpty()) {
                Log.i("Empty", "No Relevant Info Found")
            } else {
                val pattern =
                    Regex("http:\\/\\/delivery\\..+?\\.flipkart\\.com\\/([A-Za-z0-9\\?\\=&\\/\\\\\\+]+)")
                for (i in 0 until emails.await().size) {
                    try {
                        val email = ParseEmail().parseEmail(emails.await()[i])
                        val isLinkpresent = pattern.containsMatchIn(email)
                        if (isLinkpresent) {
                            Log.e("LINK PRESENT IN EMAIL NO $i", pattern.find(email)!!.value)
                        } else {
                            Log.e("LINK NOT PRESENT IN EMAIL NO$i", "Relevant link not found")
                        }
                    } catch (e: java.lang.Exception) {
                        Log.e("ERROR IN EMAIL NO $i", e.toString())
                    }
                }
            }
        }
    }
}
