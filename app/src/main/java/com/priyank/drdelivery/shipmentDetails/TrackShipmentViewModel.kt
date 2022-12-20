package com.priyank.drdelivery.shipmentDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.gmail.model.Message
import com.priyank.drdelivery.authentication.data.UserDetails
import com.priyank.drdelivery.shipmentDetails.data.remote.GetEmails
import com.priyank.drdelivery.shipmentDetails.domain.ExtractLinkFromString
import com.priyank.drdelivery.shipmentDetails.domain.ParseEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackShipmentViewModel @Inject
constructor(
    private val gsaa: GoogleSignInAccount?,
    private val gsc: GoogleSignInClient,
    private val userDetails: UserDetails,
) : ViewModel() {

    val userName = userDetails.getUserName()!!.substringBefore(" ")
    var linksFromEmails: MutableList<Triple<String, String, String?>> = mutableListOf()

    var areEmailsLoaded = flow<Boolean> {
        emit(false)
    }

    suspend fun getEmails(): List<Message> {

        val emails = GetEmails().getEmails(
            gsc.applicationContext,
            userDetails.getUserId(),
            userDetails.getUserEmail(),
        )
        return emails
    }

    fun fetchEmails() {

        GlobalScope.launch {
            areEmailsLoaded = flow {
                emit(false)
            }
            val emails = async { getEmails() }.await()
            if (emails.isEmpty()) {
                Log.i("Empty", "No Relevant Info Found")
            } else {
                for (i in emails.indices) {
                    try {
                        val email = ParseEmail().parseEmail(emails[i])
                        val link = ExtractLinkFromString().findLink(email)

                        if (link.first) {
                            Log.i("LINK FOUND IN EMAIL NO $i", link.second)
                            linksFromEmails.add(Triple("Flipkart", link.second, null))
                        } else {
                            Log.i("LINK NOT FOUND IN EMAIL NO $i", link.first.toString())
                        }
                    } catch (e: java.lang.Exception) {
                        Log.e("ERROR IN EMAIL NO $i", e.toString())
                    }
                }
            }
            delay(1000)
            areEmailsLoaded = flow {
                emit(true)
            }
        }
    }
}
