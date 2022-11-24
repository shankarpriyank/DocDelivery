package com.priyank.drdelivery.shipmentDetails

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.api.services.gmail.model.Message
import com.priyank.drdelivery.authentication.data.UserDetails
import com.priyank.drdelivery.shipmentDetails.data.remote.GetEmails
import com.priyank.drdelivery.shipmentDetails.domain.ParseEmail
import com.priyank.drdelivery.shipmentDetails.domain.model.InterestingEmail
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
    var linksFromEmails: MutableList<InterestingEmail> = mutableListOf()

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

            val pat = Regex("/\\[image:\\WTrack\\Wyour\\Wpackage\\]\\W*?<(.*?)>/ig")
            val sss =
                "https://www.amazon.in/gp/r.html?C=3A5D8OS1VAHET&K=1IXOUBB3U3OEG&M=urn:rtn:msg:20221004050136d71f0410c6284efd8455d67d0890p0eu&R=12CAGIT6533ST&T=C&U=https%3A%2F%2Fwww.amazon.in%2Fgp%2Fcss%2Fshiptrack%2Fview.html%3Fie%3DUTF8%26orderID%3D402-9374048-0465927%26orderingShipmentId%3D48268116374302%26packageId%3D1%26ref_%3Dpe_24967711_587055801_302_E_DDE&H=DGQZ8PY5WNOVFCPHNHWEWKNPM48A&ref_=pe_24967711_587055801_302_E_DDE"
            Log.e("LO", pat.containsMatchIn(sss).toString())

            val emails = async { getEmails() }.await()
            if (emails.isEmpty()) {
                Log.i("Empty", "No Relevant Info Found")
            } else {
                for (i in emails.indices) {
                    try {
                        val k = emails[i]

                        val parsedEmail = ParseEmail().parseEmail(emails[i])
                        if (parsedEmail != null) {
                            //   Log.e("LOL $i", parsedEmail.toString())
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
