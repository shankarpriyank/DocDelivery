package com.priyank.drdelivery.shipmentDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.gmail.model.Message
import com.google.api.services.gmail.model.MessagePart
import com.priyank.drdelivery.authentication.data.UserDetails
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
                        val emailSize = emails.await()[i].payload.parts.size
                        for (k in 0 until emailSize) {
                            val parsedEmail =
                                getTextFromBodyPart(emails.await()[i].payload.parts[k])
                            val isLinkPresent = pattern.containsMatchIn(parsedEmail)
                            if (isLinkPresent) {
                                Log.e("PATTERN IN EMAIL NO $i", pattern.find(parsedEmail)!!.value)
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        Log.e("ERROR IN EMAIL NO $i", e.toString())
                    }
                }
            }
        }
    }

    private fun getTextFromBodyPart(
        bodyPart: MessagePart
    ): String {
        var result: String = ""
        if (bodyPart.mimeType == "text/plain") {
            val r = bodyPart.body.decodeData()
            result = String(r)
        } else if (bodyPart.mimeType == "text/html") {
            val html = bodyPart.body.decodeData()
            result = String(html)
        }
        return result
    }
}
