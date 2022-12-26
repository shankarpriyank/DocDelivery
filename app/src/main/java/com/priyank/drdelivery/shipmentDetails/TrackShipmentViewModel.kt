package com.priyank.drdelivery.shipmentDetails

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.gmail.model.Message
import com.priyank.drdelivery.authentication.data.UserDetails
import com.priyank.drdelivery.offlineShipmentDetails.Data.GetSMS
import com.priyank.drdelivery.offlineShipmentDetails.Domain.model.RequiredSMS
import com.priyank.drdelivery.shipmentDetails.data.remote.GetEmails
import com.priyank.drdelivery.shipmentDetails.domain.ParseEmail
import com.priyank.drdelivery.shipmentDetails.domain.model.InterestingEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    var linksFromEmails: MutableList<InterestingEmail> = mutableListOf()

    var areEmailsLoaded = flow<Boolean> {
        emit(false)
    }
    var areSMSLoaded = flow<Boolean> {
        emit(false)
    }
    val onlineMode = userDetails.isLoggedIn()
    var smsList = mutableListOf<RequiredSMS>()
    fun fetchSMS(context: Context) {
        GlobalScope.launch {
            areSMSLoaded = flow {
                emit(false)
            }
            CoroutineScope(Dispatchers.IO).launch {
                smsList = GetSMS.getSMS(context)
            }
            delay(1000)
            areSMSLoaded = flow {
                emit(true)
            }
        }
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

                        val parsedEmail = ParseEmail().parseEmail(emails[i])
                        if (parsedEmail != null) {
                            Log.i("Link found in email no$i", parsedEmail.trackingLink)
                            linksFromEmails.add(parsedEmail)
                        } else {
                            Log.d("LINK NOT FOUND IN EMAIL NO $i", "")
                        }
                    } catch (e: java.lang.Exception) {
                        Log.d("ERROR IN EMAIL NO $i", e.toString())
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
